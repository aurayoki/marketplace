package com.jm.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1/goods")
public class AdvertisementController {

    @GetMapping
    public String advertisementPage() {
        return "user/advertisement_create";
    }


}
