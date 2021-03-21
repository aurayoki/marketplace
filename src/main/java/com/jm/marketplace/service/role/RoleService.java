package com.jm.marketplace.service.role;

import com.jm.marketplace.model.Role;
import com.jm.marketplace.service.general.ReadWriteService;

public interface RoleService<T, K> extends ReadWriteService<T, K> {

    Role findByName(String name);
}
