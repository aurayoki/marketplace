package com.jm.marketplace.controller.rest.goodsCategory;

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

    @Autowired
    public GoodsSubcategoryRestController(GoodsSubcategoryService goodsSubcategoryService) {
        this.goodsSubcategoryService = goodsSubcategoryService;
    }

    @GetMapping(value = "subcategory",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getSubcategoryAll() {
        return goodsSubcategoryService.findAll();
    }

    // Написать корректное название метода.
    @GetMapping(value = "subcategory/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getByGoodsCategoryId(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findByGoodsCategoryId(id);
    }

    @GetMapping(value = "subcategory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsSubcategoryDto getByIdSubCat(@PathVariable(name = "id") Long id) {
        return goodsSubcategoryService.findById(id);
    }
}
