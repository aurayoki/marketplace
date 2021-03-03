package com.jm.marketplace.controller.rest.goodsCategory;

import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods/")
public class GoodsTypeRestController {

    private final GoodsTypeService goodsTypeService;

    @Autowired
    public GoodsTypeRestController(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }

    @GetMapping(value = "type",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getTypeAll() {
        return goodsTypeService.findAll();
    }

    // Написать корректное название метода.
    @GetMapping(value = "type/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getByGoodsSubCategoryId(@PathVariable(name = "id") Long id) {
        return goodsTypeService.findByGoodsSubcategoryId(id);
    }

    @GetMapping(value = "type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsTypeDto getByIdType(@PathVariable(name = "id") Long id) {
        return goodsTypeService.findById(id);
    }
}
