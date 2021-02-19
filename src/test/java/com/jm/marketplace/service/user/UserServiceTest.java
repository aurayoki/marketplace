package com.jm.marketplace.service.user;

import com.jm.marketplace.dao.UserDao;
import com.jm.marketplace.dto.CityDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.exception.UserEmailExistsException;
import com.jm.marketplace.exception.UserPhoneExistsException;
import com.jm.marketplace.model.City;
import com.jm.marketplace.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserDao userDaoMock;

    UserDto userDto = new UserDto(1L, "Tommy", "Garposs", "1234", "asd@gmail.com",
            new CityDto(1L, "Piter"), LocalDate.of(1992, 6, 2),
            "89679681234", "null", null, null, false, null);

    User userTest = new User(1L, "Tommy", "Garposs", "1234", "asd@gmail.com",
            new City(1L, "Piter"), LocalDate.of(1992, 6, 2),
            "89679681234", "null", null, null, false, null);

    @Test
    void findByEmailShouldGetByEmail() {
        given(userDaoMock.findByEmail(userTest.getEmail())).willReturn(Optional.of(userTest));
        UserDto userDto = userService.findByEmail(userTest.getEmail());

        assertThat(userDto.getEmail()).isEqualTo(userTest.getEmail());

        verify(userDaoMock, times(1)).findByEmail(userTest.getEmail());
    }

    @Test
    void findByEmailShouldThrowUserEmailExistsException() {
        given(userDaoMock.findByEmail(anyString())).willThrow(UserEmailExistsException.class);
        Throwable throwable = catchThrowable(() -> userDaoMock.findByEmail(anyString()));

        assertThat(throwable).isInstanceOf(UserEmailExistsException.class);
    }

    @Test
    void deleteById() {
        userService.deleteById(anyLong());
        verify(userDaoMock, times(1)).deleteById(anyLong());
    }


    @Test
    void findAll() {
        given(userDaoMock.findAll()).willReturn(Collections.emptyList());

        assertThat(userService.findAll()).isEqualTo(Collections.emptyList());

        verify(userDaoMock, times(1)).findAll();
    }

    @Test
    void findUserByBirthdayShouldGetUserByBirthday() {
        given(userDaoMock.findUserByBirthday(userTest.getDate())).willReturn(Collections.emptyList());

        assertThat(userService.findUserByBirthday(userTest.getDate())).isEqualTo(Collections.emptyList());

        verify(userDaoMock, times(1)).findUserByBirthday(userTest.getDate());
    }

    @Test
    void findByIdShouldGetById() {
        given(userDaoMock.findById(userTest.getId())).willReturn(Optional.of(userTest));
        UserDto user = userService.findById(userTest.getId());

        assertThat(user.getId()).isEqualTo(userTest.getId());

        verify(userDaoMock, times(1)).findById(userTest.getId());
    }

    @Test
    void findByPhoneShouldGetByPhone() {
        given(userDaoMock.findByPhone(userTest.getPhone())).willReturn(Optional.of(userTest));
        UserDto userDto = userService.findByPhone(userTest.getPhone());

        assertThat(userDto.getPhone()).isEqualTo(userTest.getPhone());

        verify(userDaoMock, times(1)).findByPhone(userTest.getPhone());
    }

    @Test
    void findByPhoneShouldThrowUserPhoneExistsException() {
        given(userDaoMock.findByPhone(anyString())).willThrow(UserPhoneExistsException.class);
        Throwable throwable = catchThrowable(() -> userDaoMock.findByPhone(anyString()));

        assertThat(throwable).isInstanceOf(UserPhoneExistsException.class);
    }

    @Test
    void saveUser() {
        userService.saveUser(userDto);

        verify(userDaoMock, times(1)).save(any(User.class));
    }

    @Test
    void checkByEmail() {
        given(userDaoMock.findByEmail(userTest.getEmail())).willReturn(Optional.of(userTest));
        UserDto userDto = userService.findByEmail(userTest.getEmail());

        assertTrue(userService.checkByEmail(userDto.getEmail()));

        verify(userDaoMock, times(2)).findByEmail(userTest.getEmail());
    }
}

