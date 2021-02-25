package com.jm.marketplace.config.quartzconfig;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.user.UserService;
import com.jm.marketplace.util.mail.MailService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@NoArgsConstructor
@PropertySource(value = "classpath:quartz.properties")
public class OneTimeDiscount extends QuartzJobBean {
    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private MapperFacade mapperFacade;

    private String subject;
    private String message;
    private File files;

    public OneTimeDiscount(UserService userService, MailService mailService,
                           @Value("${discountSubject.string}") String subject,
                           @Value("${discountMessage.string}") String message, @Value("${upload.path}") File files) {
        this.userService = userService;
        this.mailService = mailService;
        this.subject = subject;
        this.message = message;
        this.files = files;
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<UserDto> userDtoList = userService.findAll();
        UserDto findFirstUser = userDtoList.get(getRandomNumber(0, userDtoList.size()));
        mailService.send(mapperFacade.map(findFirstUser, User.class), subject, message);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
