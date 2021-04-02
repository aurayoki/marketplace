package com.jm.marketplace.config;

import com.jm.marketplace.config.handler.LoginSuccessHandler;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;


@EnableWebSecurity
@PropertySource(value = "classpath:security.properties")
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    /**
     * Используется при созданий фильтра, который проверяет пользовательский запрос при входе через соцсеть.
     * Фильтр доступ благодаря аннотациий @EnableOAuth2Client
     */
    @Bean
    @ConfigurationProperties("google.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("google.resource")
    public ResourceServerProperties googleResource()
    {
        return new ResourceServerProperties();
    }

    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                // указываем action с формы логина
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("email")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/", "/login**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/api/**",
                                        "/admin/**",
                                        "/swagger-ui/**",
                                        "v2/api-docs").access("hasRole('ADMIN')")
                .antMatchers("/create-user").permitAll()
                .antMatchers("/email/confirm/**").permitAll()
                //страница для добавления товара
                .and().formLogin().permitAll()
                .successHandler(new LoginSuccessHandler());

        // без этого не работали методы post/put/delete рест контроллера
        http.csrf().disable();

        http.logout()
                // разрешаем делать logout всем
                .permitAll()
                // указываем URL logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном logout
                .logoutSuccessUrl("/");

        http.rememberMe().alwaysRemember(true);

        http.addFilterBefore(ssoFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Кодировщик паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter oAuth2ClientContextFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(oAuth2ClientContextFilter);
        registrationBean.setOrder(-100);
        return registrationBean;
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/oauth/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oAuth2ClientContext);

        googleFilter.setRestTemplate(googleTemplate);

        CustomUserInfoTokenServices tokenServices = new CustomUserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);
        tokenServices.setUserService(userService);

        AuthoritiesExtractor authoritiesExtractor = map -> {
            String email = (String) map.get("email");
            if (userService.checkByEmail(email)) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(userService.findByEmail(email).getRolesString().trim());
            } else {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            }
        };

        tokenServices.setAuthoritiesExtractor(authoritiesExtractor);

        return googleFilter;
    }
}
