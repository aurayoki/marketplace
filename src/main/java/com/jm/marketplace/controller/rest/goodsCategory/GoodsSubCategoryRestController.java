package com.jm.marketplace.controller.rest.goodsCategory;

import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/subcategory/")
public class GoodsSubCategoryRestController {

    private final GoodsSubcategoryService goodsSubcategoryService;

    @Autowired
    public GoodsSubCategoryRestController(GoodsSubcategoryService goodsSubcategoryService) {
        this.goodsSubcategoryService = goodsSubcategoryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getSubcategoryAll() {
        return goodsSubcategoryService.findAll();
    }

    // Написать корректное название метода.
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getByGoodsCategoryId(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findByGoodsCategoryId(id);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsSubcategoryDto getByIdSubCat(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findById(id);
    }
}
