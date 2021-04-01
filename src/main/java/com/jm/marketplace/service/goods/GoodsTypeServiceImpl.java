package com.jm.marketplace.service.goods;

import com.jm.marketplace.config.mapper.MapperFacade;
import com.jm.marketplace.dao.GoodsTypeDao;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.exception.GoodsTypeNotFoundException;
import com.jm.marketplace.model.goods.GoodsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService{

    private final GoodsTypeDao goodsTypeDao;

    @Autowired
    public GoodsTypeServiceImpl(GoodsTypeDao goodsTypeDao) {
        this.goodsTypeDao = goodsTypeDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsType> findAll() {
        return goodsTypeDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsType> findByGoodsSubcategoryId(Long id) {
        return goodsTypeDao.findByGoodsSubcategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public GoodsType findById(Long id) {
        GoodsType goodsType = goodsTypeDao.findById(id).orElseThrow(() ->
                new GoodsTypeNotFoundException(String.format("Goods type not found by id: %s", id)));
        return goodsType;
    }

}
