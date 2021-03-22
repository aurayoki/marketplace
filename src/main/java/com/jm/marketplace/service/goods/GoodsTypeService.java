package com.jm.marketplace.service.goods;

import com.jm.marketplace.model.goods.GoodsType;
import com.jm.marketplace.service.general.ReadWriteService;

import java.util.List;

public interface GoodsTypeService extends ReadWriteService<GoodsType, Long> {

    List<GoodsType> findByGoodsSubcategoryId(Long id);
}
