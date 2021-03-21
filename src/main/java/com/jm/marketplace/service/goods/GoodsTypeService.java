package com.jm.marketplace.service.goods;

import com.jm.marketplace.model.goods.GoodsType;
import com.jm.marketplace.service.general.ReadWriteService;

import java.util.List;

public interface GoodsTypeService<T, K> extends ReadWriteService<T, K> {

    List<GoodsType> findByGoodsSubcategoryId(Long id);
}
