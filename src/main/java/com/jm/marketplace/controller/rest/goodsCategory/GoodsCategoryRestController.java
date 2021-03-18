package com.jm.marketplace.controller.rest.goodsCategory;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods/")
public class GoodsCategoryRestController {

    private final GoodsCategoryService goodsCategoryService;
    private MapperFacade mapperFacade;

    @Autowired
    public GoodsCategoryRestController(GoodsCategoryService goodsCategoryService, MapperFacade mapperFacade) {
        this.goodsCategoryService = goodsCategoryService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping(value = "category", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsCategoryDto> getCategoryAll() {
        return mapperFacade.mapAsList(goodsCategoryService.findAll(), GoodsCategoryDto.class);
    }

    @GetMapping(value = "category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsCategoryDto getByIdCat(@PathVariable(name = "id") Long id) {
        return mapperFacade.map(goodsCategoryService.findById(id),GoodsCategoryDto.class);
    }
}
