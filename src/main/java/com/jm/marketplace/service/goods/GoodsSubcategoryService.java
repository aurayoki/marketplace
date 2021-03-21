package com.jm.marketplace.service.goods;

import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.service.general.ReadWriteService;

import java.util.List;

public interface GoodsSubcategoryService <T, K> extends ReadWriteService<T, K> {

    List<GoodsSubcategory> findByGoodsCategoryId(Long id);

}
