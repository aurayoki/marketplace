package com.jm.marketplace.controller.rest;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods")
public class GoodsRestController {

    private final AdvertisementService advertisementService;
    private final GoodsCategoryService goodsCategoryService;
    private final GoodsSubcategoryService goodsSubcategoryService;
    private final GoodsTypeService goodsTypeService;

    @Autowired
    public GoodsRestController(AdvertisementService advertisementService, GoodsCategoryService goodsCategoryService, GoodsSubcategoryService goodsSubcategoryService, GoodsTypeService goodsTypeService) {
        this.advertisementService = advertisementService;
        this.goodsCategoryService = goodsCategoryService;
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.goodsTypeService = goodsTypeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementDto> getAll() {
        return advertisementService.findAll();
    }
    @GetMapping(value = "/cat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsCategoryDto> getCategoryAll() {
        return goodsCategoryService.findAll();
    }

    @GetMapping(value = "/subcat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsSubcategoryDto> getSubcategoryAll() {
        return goodsSubcategoryService.findAll();
    }

    @GetMapping(value = "/type", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getTypeAll() {
        return goodsTypeService.findAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDto getById(@PathVariable(name = "id") Long id) {
        return advertisementService.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid AdvertisementDto advertisementDto) {
        advertisementService.saveOrUpdate(advertisementDto);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody @Valid AdvertisementDto advertisementDto) {
        advertisementService.saveOrUpdate(advertisementDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(name = "id") Long id) {
        advertisementService.deleteById(id);
    }
}
