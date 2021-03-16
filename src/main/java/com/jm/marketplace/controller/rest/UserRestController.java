package com.jm.marketplace.controller.rest;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;
    private MapperFacade mapperFacade;

    @Autowired
    public UserRestController(UserService userService, MapperFacade mapperFacade) {
        this.userService = userService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> showAllUsers() {
        return mapperFacade.mapAsList(userService.findAll(), UserDto.class);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable(name = "id") Long id) {
        return mapperFacade.map(userService.findById(id), UserDto.class);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewUser(@RequestBody UserDto userDto) {
        userService.saveUser(mapperFacade.map(userDto, User.class));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void editUser(@RequestBody UserDto userDto) {
        userService.saveUser(mapperFacade.map(userDto, User.class));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteById(id);
    }

}
