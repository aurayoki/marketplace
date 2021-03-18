package com.jm.marketplace.controller.rest.goodsCategory;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods/")
public class GoodsSubcategoryRestController {

    private final GoodsSubcategoryService goodsSubcategoryService;
    private MapperFacade mapperFacade;

    @Autowired
    public GoodsSubcategoryRestController(GoodsSubcategoryService goodsSubcategoryService, MapperFacade mapperFacade) {
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping(value = "subcategory",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getSubcategoryAll() {
        return mapperFacade.mapAsList(goodsSubcategoryService.findAll(), GoodsSubcategoryDto.class);
    }

    // Написать корректное название метода.
    @GetMapping(value = "subcategory/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getByGoodsCategoryId(@PathVariable(name = "id") Long id) {
        return mapperFacade.mapAsList(goodsSubcategoryService.findByGoodsCategoryId(id), GoodsSubcategoryDto.class);
    }

    @GetMapping(value = "subcategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsSubcategoryDto getByIdSubCat(@PathVariable(name = "id") Long id) {
        return mapperFacade.map(goodsSubcategoryService.findById(id), GoodsSubcategoryDto.class);
    }
}
