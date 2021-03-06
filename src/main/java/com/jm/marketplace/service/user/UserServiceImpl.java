package com.jm.marketplace.service.user;

import com.jm.marketplace.dao.RoleDao;
import com.jm.marketplace.dao.UserDao;
import com.jm.marketplace.exception.*;
import com.jm.marketplace.model.Role;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import com.jm.marketplace.util.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ReadWriteServiceImpl<User, Long> implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    @Value("${role.name.user}")
    private String userRole;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder, MailService mailService) {
        super(userDao);
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        if (user.getId() == null) {
            processNewUser(user);
        } else {
            user = updateUser(user);
        }
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userDao.findById(id).orElseThrow(() ->
                new UserNotFoundException(String.format("User not found by id: %s", id))));

    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findUserByBirthday(LocalDate date) {
        return userDao.findUserByBirthday(date);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(() ->
                new UserEmailExistsException("???????????????????????? ?? ?????????? ???????????? ???? ????????????"));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkByEmail(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByPhone(String phone) {
        return userDao.findByPhone(phone).orElseThrow(() ->
                new UserPhoneExistsException("???????????????????????? ?? ?????????? ?????????????? ???????????????? ???? ????????????"));
    }

    @Override
    public void activateUser(User user) {
        user.setActive(true);
        saveOrUpdate(user);
    }

    @Override
    public User findByUniqueCode(String uniqueCode) {
        return userDao.findUserByUniqueCode(uniqueCode).orElseThrow(() ->
                new UserUniqueCodeNotFoundException(String.format("UniqueCode '%s' not found", uniqueCode)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Email '%s' not found", email)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * ???? ?????????????????? ?????????? ???????????? ?????????????????? Authorities
     */
    public Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    private void processNewUser(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role role = roleDao.findByName(userRole).orElseThrow(() ->
                    new RoleNotFoundException(String.format("Role not found by name: %s", userRole)));
            user.setRoles(Set.of(role));
        }
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUniqueCode(UUID.randomUUID().toString());
        mailService.send(user, "?????????????????????????? ?????????????????????? ??????????", getVerificationURL(user.getUniqueCode()));
    }

    private String getVerificationURL(String uniqueCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?????? ?????????????????????????? ?????????? ?????????????????? ???? ????????????: ")
                .append("http://localhost:8080/email/confirm/")
                .append(uniqueCode);
        return stringBuilder.toString();
    }

    public User updateUser(User user) {
        User userFromDB = userDao.findByEmail(user.getEmail()).orElseThrow(() -> new UserEmailExistsException("???????????????????????? ?? ?????????? ???????????? ???? ????????????"));
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setDate(user.getDate());
        userFromDB.setPhone(user.getPhone());
        userFromDB.setActive(user.getActive());
        userFromDB.setUserImg(user.getUserImg());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getCity() != null) {
            userFromDB.setCity(user.getCity());
        }
        return userFromDB;
    }
}
