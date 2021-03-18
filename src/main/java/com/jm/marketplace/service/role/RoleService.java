package com.jm.marketplace.service.role;

import com.jm.marketplace.dto.RoleDto;
import com.jm.marketplace.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();
    Role findById(Long id);
    Role findByName(String name);
}
