package com.jm.marketplace.controller;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.CityDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.City;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.city.CityService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService<User, Long> userService;
    private final CityService<City, Long> cityService;
    private final MapperFacade mapperFacade;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserController(UserService<User, Long> userService, CityService<City, Long> cityService, MapperFacade mapperFacade) {
        this.userService = userService;
        this.cityService = cityService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping
    public String showUser(Model model, Principal principal) {
        UserDto userDto = mapperFacade.map(userService.findByEmail(principal.getName()), UserDto.class);
        model.addAttribute("userDto", userDto);
        return "user/index";
    }

    @GetMapping(value = "/edit")
    public String showUserEdit(Model model, Principal principal) {
        List<CityDto> cityDto = mapperFacade.mapAsList(cityService.findAll(), CityDto.class);
        UserDto userDto = mapperFacade.map(userService.findByEmail(principal.getName()), UserDto.class);
        model.addAttribute("userDto", userDto);
        model.addAttribute("cities", cityDto);
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
                    .insert(0, "/")
                    .insert(0, uploadPath);
            userDto.getMultipartFile().transferTo(new File(stringBuilder.toString()));
        }
        userService.saveOrUpdate(mapperFacade.map(userDto, User.class));
        return "user/edit";
    }
}
