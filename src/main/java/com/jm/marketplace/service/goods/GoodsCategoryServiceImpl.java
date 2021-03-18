package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsCategoryDao;
import com.jm.marketplace.exception.GoodsCategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsCategory;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private final GoodsCategoryDao goodsCategoryDao;

    @Autowired
    public GoodsCategoryServiceImpl(GoodsCategoryDao goodsCategoryDao) {
        this.goodsCategoryDao = goodsCategoryDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsCategory> findAll() {
        return goodsCategoryDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public GoodsCategory findById(Long id) {
        GoodsCategory goodsCategory = goodsCategoryDao.findById(id).orElseThrow(() ->
                new GoodsCategoryNotFoundException(String.format("Goods category not found by id: %s", id)));
        return goodsCategory;
    }
}
