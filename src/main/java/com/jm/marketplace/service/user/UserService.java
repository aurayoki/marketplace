package com.jm.marketplace.service.user;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser (User user);

    List<User> findUserByBirthday(LocalDate date);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);

    User findByEmail(String email);

    boolean checkByEmail(String email);

    User findByPhone(String phone);

    void activateUser(User user);

    User findByUniqueCode(String uniqueCode);
}
