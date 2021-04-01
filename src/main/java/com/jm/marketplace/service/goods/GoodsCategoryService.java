package com.jm.marketplace.service.goods;

import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.model.goods.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService {

    List<GoodsCategory> findAll();

    GoodsCategory findById(Long id);

}
