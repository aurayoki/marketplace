package com.jm.marketplace.controller;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.CityDto;
import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.search.advertisement.keywords.service.SearchingByKeywordService;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.city.CityService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping(value = "/")
public class MainController {

    private final AdvertisementService advertisementService;
    private final CityService cityService;
    private final UserService userService;
    private final SearchingByKeywordService keywordService;

    private MapperFacade mapperFacade;

    @Autowired
    public void setMapperFacade(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    @Autowired
    public MainController(AdvertisementService advertisementService, CityService cityService, UserService userService, SearchingByKeywordService keywordService) {
        this.advertisementService = advertisementService;
        this.cityService = cityService;
        this.userService = userService;
        this.keywordService = keywordService;
    }

    @GetMapping
    public String showMainPage(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(value = "search", required = false) String search) {
        List<CityDto> cityDto = mapperFacade.mapAsList(cityService.findAll(), CityDto.class);
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("cities", cityDto);
        if (search != null) {
            model.addAttribute("allGoods", keywordService.findByKeyword(search));
        } else {
            model.addAttribute("allGoods", advertisementService.findAll(page));
        }
        return "index";
    }

    @PostMapping(value = "/create-user")
    public String create(@ModelAttribute("userDto") UserDto userDto) {
        userService.saveOrUpdate(mapperFacade.map(userDto, User.class));
        return "/email/confirm/verification";
    }

    @GetMapping(value = "/email/confirm/{code}")
    public String create(@PathVariable(value = "code") String code) {
        UserDto userDto = mapperFacade.map(userService.findByUniqueCode(code), UserDto.class);
        userService.activateUser(mapperFacade.map(userDto, User.class));
        return "/email/confirm/index";
    }

    @GetMapping(value = "/good/{id}")
    public String showAdvertisementById(@PathVariable(value = "id") Long id,
                                        Model model) {
        AdvertisementDto advertisement = mapperFacade.map(advertisementService.findById(id), AdvertisementDto.class);
        model.addAttribute("good", advertisement);
        return "advertisementPage";
    }

    @GetMapping("/login")
    public String mainPage() {
        return "/login";
    }
}
