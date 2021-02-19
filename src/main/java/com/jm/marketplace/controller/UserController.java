package com.jm.marketplace.controller;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.service.city.CityService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final CityService cityService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserController(UserService userService, CityService cityService) {
        this.userService = userService;
        this.cityService = cityService;
    }

    @GetMapping
    public String showUser(Model model, Principal principal) {
        model.addAttribute("userDto", userService.findByEmail(principal.getName()));
        return "user/index";
    }

    @GetMapping(value = "/edit")
    public String showUserEdit(Model model, Principal principal) {
        model.addAttribute("userDto", userService.findByEmail(principal.getName()));
        model.addAttribute("cities", cityService.getAllCity());
        return "user/edit";
    }

    @PostMapping(value = "/edit/save")
    public String saveUserEdit(@ModelAttribute UserDto userDto) throws IOException {
        if (userDto.getMultipartFile() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(UUID.randomUUID().toString()).append(".")
                    .append(userDto.getMultipartFile().getOriginalFilename());
            userDto.setUserImg(stringBuilder.toString());
            stringBuilder
                    .insert(0,"/")
                    .insert(0, uploadPath);
            userDto.getMultipartFile().transferTo(new File(stringBuilder.toString()));
        }
        userService.saveUser(userDto);
        return "user/edit";
    }
}
