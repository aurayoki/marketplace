package com.jm.marketplace.service.goods;

import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.model.goods.GoodsType;

import java.util.List;

public interface GoodsTypeService {

    List<GoodsType> findAll();

    List<GoodsType> findByGoodsSubcategoryId(Long id);

    GoodsType findById(Long id);

}
