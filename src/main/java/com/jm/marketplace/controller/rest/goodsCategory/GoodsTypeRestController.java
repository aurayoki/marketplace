package com.jm.marketplace.controller.rest.goodsCategory;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.model.goods.GoodsType;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/goods/")
public class GoodsTypeRestController {

    private final GoodsTypeService<GoodsType, Long> goodsTypeService;
    private MapperFacade mapperFacade;

    @Autowired
    public GoodsTypeRestController(GoodsTypeService<GoodsType, Long> goodsTypeService,MapperFacade mapperFacade) {
        this.goodsTypeService = goodsTypeService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping(value = "type",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getTypeAll() {
        return mapperFacade.mapAsList(goodsTypeService.findAll(), GoodsTypeDto.class);
    }

    // Написать корректное название метода.
    @GetMapping(value = "type/good/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<GoodsTypeDto> getByGoodsSubCategoryId(@PathVariable(name = "id") Long id) {
        return mapperFacade.mapAsList(goodsTypeService.findByGoodsSubcategoryId(id), GoodsTypeDto.class);
    }

    @GetMapping(value = "type/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GoodsTypeDto getByIdType(@PathVariable(name = "id") Long id) {
        return mapperFacade.map(goodsTypeService.findById(id), GoodsTypeDto.class);
    }
}
