package com.jm.marketplace.service.role;

import com.jm.marketplace.dao.RoleDao;
import com.jm.marketplace.exception.RoleNotFoundException;
import com.jm.marketplace.model.Role;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService<Role, Long> {

    private final ReadWriteService<Role, Long> readWriteService;
    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(@Lazy ReadWriteService<Role, Long> readWriteService, RoleDao roleDao) {
        this.readWriteService = readWriteService;
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Role> findAll() {
        return readWriteService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Role> findById(Long id) {
        Role role = readWriteService.findById(id).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role not found by id: %s", id)));
        return Optional.ofNullable(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findByName(String name) {
        Role role = roleDao.findByName(name).orElseThrow(() ->
                new RoleNotFoundException(String.format("Role not found by name: %s", name)));
        return role;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveOrUpdate(Role role) {

    }
}
