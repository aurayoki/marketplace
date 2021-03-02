package com.jm.marketplace.controller.rest.goodsCategory;

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

    @Autowired
    public GoodsCategoryRestController(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    @GetMapping(value = "category", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsCategoryDto> getCategoryAll() {
        return goodsCategoryService.findAll();
    }

    @GetMapping(value = "category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsCategoryDto getByIdCat(@PathVariable(name = "id") Long id) {
        return goodsCategoryService.findById(id);
    }
}
