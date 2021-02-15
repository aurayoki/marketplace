package com.jm.marketplace.controller;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.city.CityService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private final AdvertisementService advertisementService;
    private final CityService cityService;
    private final UserService userService;

    @Autowired
    public MainController(AdvertisementService advertisementService, CityService cityService, UserService userService) {
        this.advertisementService = advertisementService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @GetMapping
    public String showMainPage(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        model.addAttribute("allGoods", advertisementService.findAll(page));
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("cities", cityService.getAllCity());
        return "index";
    }

    @PostMapping(value = "/create-user")
    public String create(@ModelAttribute("userDto") UserDto userDto) {
        userService.saveUser(userDto);
        return "/email/confirm/verification";
    }

    @GetMapping(value = "/email/confirm/{code}")
    public String create(@PathVariable(value = "code") String code) {
        UserDto userDto = userService.findByUniqueCode(code);
        userService.activateUser(userDto);
        return "/email/confirm/index";
    }
}
