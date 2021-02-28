package com.jm.marketplace.controller.rest;

import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods")
public class GoodsRestController {

    private final GoodsCategoryService goodsCategoryService;
    private final GoodsSubcategoryService goodsSubcategoryService;
    private final GoodsTypeService goodsTypeService;

    @Autowired
    public GoodsRestController(GoodsCategoryService goodsCategoryService,
                               GoodsSubcategoryService goodsSubcategoryService,
                               GoodsTypeService goodsTypeService) {
        this.goodsCategoryService = goodsCategoryService;
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.goodsTypeService = goodsTypeService;
    }

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsCategoryDto> getCategoryAll() {
        return goodsCategoryService.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsCategoryDto getByIdCat(@PathVariable(name = "id") Long id) {
        return goodsCategoryService.findById(id);
    }

    @GetMapping(value = "/subcategory", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getSubcategoryAll() {
        return goodsSubcategoryService.findAll();
    }

    // Написать корректное название метода.
    @GetMapping(value = "/subcategory/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getByGoodsCategoryId(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findByGoodsCategoryId(id);
    }

    @GetMapping(value = "/subcategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsSubcategoryDto getByIdSubCat(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findById(id);
    }

    @GetMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getTypeAll() {
        return goodsTypeService.findAll();
    }

    // Написать корректное название метода.
    @GetMapping(value = "/type/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getByGoodsSubCategoryId(@PathVariable(name = "id") Long id) {
        return goodsTypeService.findByGoodsSubcategoryId(id);
    }

    @GetMapping(value = "/type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsTypeDto getByIdType(@PathVariable(name = "id") Long id) {
        return goodsTypeService.findById(id);
    }
}
