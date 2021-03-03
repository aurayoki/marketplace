package com.jm.marketplace.service.role;

import com.jm.marketplace.dao.RoleDao;
import com.jm.marketplace.dto.RoleDto;
import com.jm.marketplace.exception.RoleNotFoundException;
import com.jm.marketplace.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleDao roleDaoMock;

    Role role = new Role(1L, "Admin");

    @Test
    void findByIdShouldGetById() {
        given(roleDaoMock.findById(role.getId())).willReturn(Optional.of(role));
        RoleDto roleDto = roleService.findById(role.getId());
        assertThat(roleDto.getId().compareTo(role.getId()));
        verify(roleDaoMock, times(1)).findById(role.getId());
    }


    @Test
    void findByNameShouldGetByName() {
        given(roleDaoMock.findByName(role.getName())).willReturn(Optional.of(role));
        RoleDto roleDto = roleService.findByName(role.getName());
        assertThat(roleDto.getName()).isEqualTo(role.getName());
        verify(roleDaoMock, times(1)).findByName(role.getName());
    }

    @Test
    void findAll() {
        given(roleDaoMock.findAll()).willReturn(Collections.emptyList());
        assertThat(roleService.findAll()).isEqualTo(Collections.emptyList());
        verify(roleDaoMock, times(1)).findAll();
    }

    @Test
    void findByIdShouldThrowRoleIdExistsException() {
        given(roleDaoMock.findById(anyLong())).willThrow(RoleNotFoundException.class);
        Throwable throwable = catchThrowable(() -> roleService.findById(anyLong()));

        assertThat(throwable).isInstanceOf(RoleNotFoundException.class);
    }

    @Test
    void findByNameShouldThrowRoleNameExistsException() {
        given(roleDaoMock.findByName(anyString())).willThrow(RoleNotFoundException.class);
        Throwable throwable = catchThrowable(() -> roleService.findByName(anyString()));

        assertThat(throwable).isInstanceOf(RoleNotFoundException.class);
    }
}
