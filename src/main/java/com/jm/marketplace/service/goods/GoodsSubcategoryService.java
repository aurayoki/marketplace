package com.jm.marketplace.service.goods;

import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.model.goods.GoodsSubcategory;

import java.util.List;

public interface GoodsSubcategoryService {

    List<GoodsSubcategory> findAll();

    List<GoodsSubcategory> findByGoodsCategoryId(Long id);

    GoodsSubcategory findById(Long id);

}
