package com.jm.marketplace.service.goods;

import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.service.general.ReadWriteService;

import java.util.List;

public interface GoodsSubcategoryService extends ReadWriteService<GoodsSubcategory, Long> {

    List<GoodsSubcategory> findByGoodsCategoryId(Long id);

}
