package com.jm.marketplace.password.reset.controller;

import com.jm.marketplace.dao.UserDao;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.User;
import com.jm.marketplace.password.reset.model.PasswordResetToken;
import com.jm.marketplace.password.reset.service.PasswordResetTokenService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;

@Controller
public class RecoveryPasswordController {

    private UserDao userDao;

    private PasswordResetTokenService passwordResetTokenService;

    private UserService userService;

    private static String addToken;

    @Autowired
    public RecoveryPasswordController(UserDao userDao, PasswordResetTokenService passwordResetTokenService, UserService userService) {
        this.userDao = userDao;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
    }

    @GetMapping("/recovery/email/reset")
    public ModelAndView showChangePasswordPage(@RequestParam(required = false) String token) {
        Calendar calendar = Calendar.getInstance();
        PasswordResetToken passwordResetToken = passwordResetTokenService.getVerificationToken(token);
        if (passwordResetToken != null && !passwordResetToken.getExpiryDate().before(calendar.getTime())) {
            addToken = token;
            return new ModelAndView("fragments/recoverPasswordLink");
        } else {
            return new ModelAndView("index");
        }
    }

    @PostMapping("/recovery/email/reset")
    public ModelAndView changePassword(@RequestParam String password, final RedirectAttributes redirectAttributes) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.getVerificationToken(addToken);
        String email = passwordResetToken.getEmail();
        if (password.length() > 7&&userDao.findByEmail(email).isPresent()) {
            User user = userDao.findByEmail(email).get();
            user.setPassword(password);
            redirectAttributes.addFlashAttribute("message", "Пароль был изменен");
            userDao.save(user);
            passwordResetTokenService.deletePasswordResetToken(email);
        } else {
            redirectAttributes.addFlashAttribute("message", "Пароль должен содержать более 7 символов");
        }
        return new ModelAndView("index");
    }
}
