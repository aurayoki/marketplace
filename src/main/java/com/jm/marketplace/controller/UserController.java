package com.jm.marketplace.controller;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.service.city.CityService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private final CityService cityService;

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
    public String saveUserEdit(@ModelAttribute UserDto userDto) {
        userService.saveUser(userDto);
        return "user/edit";
    }
}
