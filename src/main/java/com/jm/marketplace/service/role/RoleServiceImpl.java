package com.jm.marketplace.service.role;

import com.jm.marketplace.dao.RoleDao;
import com.jm.marketplace.exception.RoleNotFoundException;
import com.jm.marketplace.model.Role;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl extends ReadWriteServiceImpl<Role, Long>  implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findById(Long id) {
        Role role = roleDao.findById(id).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role not found by id: %s", id)));
        return Optional.ofNullable(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role not found by name: %s", name)));
    }
}
