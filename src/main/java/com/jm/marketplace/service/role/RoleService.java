package com.jm.marketplace.service.role;

import com.jm.marketplace.model.Role;
import com.jm.marketplace.service.general.ReadWriteService;

public interface RoleService extends ReadWriteService<Role, Long> {

    Role findByName(String name);
}
