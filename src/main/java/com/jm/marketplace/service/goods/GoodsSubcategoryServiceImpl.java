package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsSubcategoryDao;
import com.jm.marketplace.exception.GoodsSubcategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsSubcategoryServiceImpl extends ReadWriteServiceImpl<GoodsSubcategory, Long> implements GoodsSubcategoryService {

    private final GoodsSubcategoryDao goodsSubcategoryDao;

    @Autowired
    public GoodsSubcategoryServiceImpl(GoodsSubcategoryDao goodsSubcategoryDao) {
        super(goodsSubcategoryDao);
        this.goodsSubcategoryDao = goodsSubcategoryDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsSubcategory> findByGoodsCategoryId(Long id) {
        return goodsSubcategoryDao.findByGoodsCategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsSubcategory> findById(Long id) {
        GoodsSubcategory goodsSubcategory = goodsSubcategoryDao.findById(id).orElseThrow(() ->
                new GoodsSubcategoryNotFoundException(String.format("Goods subcategory not found by id: %s", id)));
        return Optional.ofNullable(goodsSubcategory);
    }
}
