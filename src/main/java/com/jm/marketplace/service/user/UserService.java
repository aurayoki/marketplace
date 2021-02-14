package com.jm.marketplace.service.user;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;

public interface UserService extends UserDetailsService {

    void saveUser (UserDto userDto);

    List<User> findUserByBirthday(LocalDate date);

    List<UserDto> findAll();

    UserDto findById(Long id);

    void deleteById(Long id);

    UserDto findByEmail(String email);

    boolean checkByEmail(String email);

    UserDto findByPhone(String phone);

}
