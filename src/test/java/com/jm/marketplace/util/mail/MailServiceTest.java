package com.jm.marketplace.util.mail;

import com.jm.marketplace.model.City;
import com.jm.marketplace.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class MailServiceTest {

    @Autowired
    private MailService mailService;

    User user = new User(1L, "Anton", "Vladimirovich", "asdfsdf", "test01marketplace@gmail.com",
            new City(1L, "Moscov"), LocalDate.of(1996, 2, 23),
            "89634325677", "null", null, null, false, null);

    List<User> userList = new ArrayList<>();

    @Test
    void testSend() {
        assertDoesNotThrow(() -> mailService.send(user, "Test", "TextTest"));
        assertDoesNotThrow(() -> mailService.send(user, "Test", "TextTest", new File("/home/hasan/Рабочий стол/Папка/students_marketplace/pom.xml")));
    }

    @Test
    void testBroadcast() {
        userList.add(user);
        assertDoesNotThrow(() -> mailService.broadcast(userList, "Test", "TextTest"));
        assertDoesNotThrow(() -> mailService.broadcast(userList, "Test", "TextTest", new File("/home/hasan/Рабочий стол/Папка/students_marketplace/pom.xml")));
    }
}