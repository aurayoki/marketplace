package com.jm.marketplace.controller;

import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdvertisementService advertisementService;
    private final UserService userService;

    @Autowired
    public AdminController(AdvertisementService advertisementService, UserService userService) {
        this.advertisementService = advertisementService;
        this.userService = userService;
    }

    @GetMapping
    public String showAdminPage(Model model,
                                @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }

    @GetMapping(value = "/adv_in_active")
    public String getPageAdvertisementInActive(Model model,
                                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false) Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("active", "true");
        model.addAttribute("advertisementInActive", advertisementService.findAll(page, params));
        return "admin_advertisement_in_active";
    }
}
