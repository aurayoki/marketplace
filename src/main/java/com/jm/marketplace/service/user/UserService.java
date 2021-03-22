package com.jm.marketplace.service.user;

import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService, ReadWriteService<User, Long> {

    List<User> findUserByBirthday(LocalDate date);

    User findByEmail(String email);

    boolean checkByEmail(String email);

    User findByPhone(String phone);

    void activateUser(User user);

    User findByUniqueCode(String uniqueCode);
}
