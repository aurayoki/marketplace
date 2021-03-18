package com.jm.marketplace.service.goods;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.GoodsSubcategoryDao;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.exception.GoodsSubcategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsSubcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsSubcategoryServiceImpl implements GoodsSubcategoryService{

    private final GoodsSubcategoryDao goodsSubcategoryDao;

    @Autowired
    public GoodsSubcategoryServiceImpl(GoodsSubcategoryDao goodsSubcategoryDao) {
        this.goodsSubcategoryDao = goodsSubcategoryDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsSubcategory> findAll() {
        return goodsSubcategoryDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsSubcategory> findByGoodsCategoryId(Long id) {
        return goodsSubcategoryDao.findByGoodsCategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public GoodsSubcategory findById(Long id) {
        GoodsSubcategory goodsSubcategory = goodsSubcategoryDao.findById(id).orElseThrow(() ->
                new GoodsSubcategoryNotFoundException(String.format("Goods subcategory not found by id: %s", id)));
        return goodsSubcategory;
    }

}
