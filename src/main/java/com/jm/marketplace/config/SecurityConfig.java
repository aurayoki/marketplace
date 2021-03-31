package com.jm.marketplace.config;

import com.jm.marketplace.config.handler.SuccessUserHandler;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@PropertySource(value = "classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private SuccessUserHandler successUserHandler;

    @Autowired
    public void setUserService(UserService userService,
                               SuccessUserHandler successUserHandler) {
        this.userService = userService;
        this.successUserHandler = successUserHandler;
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
                .successHandler(successUserHandler)
                .usernameParameter("email")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/create-user").permitAll()
                .antMatchers("/email/confirm/**").permitAll()
                //страница для добавления товара
                .antMatchers("/admin/**", "profile/**").access("hasRole('ADMIN')")
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
}
