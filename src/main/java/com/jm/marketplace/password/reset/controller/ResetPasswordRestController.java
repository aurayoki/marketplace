package com.jm.marketplace.password.reset.controller;

import com.jm.marketplace.dao.UserDao;
import com.jm.marketplace.exception.UserEmailExistsException;
import com.jm.marketplace.model.User;
import com.jm.marketplace.password.reset.service.PasswordResetTokenService;
import com.jm.marketplace.service.user.UserService;
import com.jm.marketplace.util.mail.MailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ResetPasswordRestController {

    private UserDao userDao;

    private UserService userService;

    private MailService mailService;

    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public ResetPasswordRestController(UserDao userDao, UserService userService, MailService mailService,
                                       PasswordResetTokenService passwordResetTokenService) {
        this.userDao = userDao;
        this.userService = userService;
        this.mailService = mailService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping(value = "/api/recovery/email")
    public User resetLink(@RequestBody JSONObject json, HttpServletRequest request) {
        String email = json.getString("type");
        Optional<User> optionalUser = userDao.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserEmailExistsException();
        }
        User user = optionalUser.get();
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(email, token);
        String appUrl = String.join("", request.getRequestURL(), "/reset?token=", token);
        mailService.send(user, "Восстановление пароля", "Ссылка для восстановления пароля, \n".concat(appUrl));
        return user;
    }
}
