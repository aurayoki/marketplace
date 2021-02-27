package com.jm.marketplace.util.mail;

import com.jm.marketplace.model.City;
import com.jm.marketplace.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private MailService mailService;

    @SpyBean
    private JavaMailSender javaMailSender;

    private final String pathTestFile;
    public MailServiceTest() throws URISyntaxException {
        pathTestFile = getClass().getResource("/test_empty_file.txt").toURI().getPath();
    }

    User user = new User(1L, "Anton", "Vladimirovich", "asdfsdf", "test01marketplace@gmail.com",
            new City(1L, "Moscov"), LocalDate.of(1996, 2, 23),
            "89634325677", "null", null, null, false, null);

    List<User> userList = new ArrayList<>();

    @Test
    void testSend() {
        mailService.send(user, "Test", "TextTest");
        mailService.send(user, "Test", "TextTest", new File(pathTestFile));
        verify(javaMailSender, times(1)).send(new SimpleMailMessage[]{any(SimpleMailMessage.class)});
        verify(javaMailSender, times(1)).send(new MimeMessage[]{any(MimeMessage.class)});
    }

    @Test
    void testBroadcast() {
        userList.add(user);
        mailService.broadcast(userList, "Test", "TextTest");
        mailService.broadcast(userList, "Test", "TextTest", new File(pathTestFile));

        verify(javaMailSender, times(1)).send(new SimpleMailMessage[]{any(SimpleMailMessage.class)});
        verify(javaMailSender, times(1)).send(new MimeMessage[]{any(MimeMessage.class)});
    }
}