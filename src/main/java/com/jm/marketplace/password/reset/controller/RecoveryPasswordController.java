package com.jm.marketplace.password.reset.controller;

import com.jm.marketplace.dao.UserDao;
import com.jm.marketplace.model.User;
import com.jm.marketplace.password.reset.model.PasswordResetToken;
import com.jm.marketplace.password.reset.service.PasswordResetTokenService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Optional;

@Controller
public class RecoveryPasswordController {

    private UserDao userDao;

    private PasswordResetTokenService passwordResetTokenService;

    private UserService userService;

    private static String addToken;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RecoveryPasswordController(UserDao userDao, PasswordResetTokenService passwordResetTokenService,
                                      UserService userService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/recovery/email/reset")
    public String showChangePasswordPage(@RequestParam(required = false) String token) {
        Calendar calendar = Calendar.getInstance();
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenService.getVerificationToken(token);
        if (passwordResetToken.isPresent() && !passwordResetToken.get().getExpiryDate().before(calendar.getTime())) {
            addToken = token;
            return "fragments/recoverPasswordLink";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/api/recovery/email/reset")
    public String changePassword(@RequestParam String password, final RedirectAttributes redirectAttributes) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenService.getVerificationToken(addToken);
        String email = passwordResetToken.get().getEmail();
        if (password.length() > 7 && userDao.findByEmail(email).isPresent()) {
            User user = userDao.findByEmail(email).get();
            user.setPassword(passwordEncoder.encode(password));
            redirectAttributes.addFlashAttribute("message", "Пароль был изменен");
            userDao.save(user);
            passwordResetTokenService.deletePasswordResetToken(email);
        } else {
            redirectAttributes.addFlashAttribute("message", "Пароль должен содержать более 7 символов");
        }
        return "redirect:/";
    }
}
