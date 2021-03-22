package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsTypeDao;
import com.jm.marketplace.exception.GoodsTypeNotFoundException;
import com.jm.marketplace.model.goods.GoodsType;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsTypeServiceImpl extends ReadWriteServiceImpl<GoodsType, Long> implements GoodsTypeService {

    private final GoodsTypeDao goodsTypeDao;

    @Autowired
    public GoodsTypeServiceImpl(GoodsTypeDao goodsTypeDao) {
        super(goodsTypeDao);
        this.goodsTypeDao = goodsTypeDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsType> findByGoodsSubcategoryId(Long id) {
        return goodsTypeDao.findByGoodsSubcategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsType> findById(Long id) {
        GoodsType goodsType = goodsTypeDao.findById(id).orElseThrow(() ->
                new GoodsTypeNotFoundException(String.format("Goods type not found by id: %s", id)));
        return Optional.ofNullable(goodsType);
    }
}
