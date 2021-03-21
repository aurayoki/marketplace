package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsTypeDao;
import com.jm.marketplace.exception.GoodsTypeNotFoundException;
import com.jm.marketplace.model.goods.GoodsType;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService<GoodsType, Long> {

    private final GoodsTypeDao goodsTypeDao;
    private final ReadWriteService<GoodsType, Long> readWriteService;

    @Autowired
    public GoodsTypeServiceImpl(GoodsTypeDao goodsTypeDao, @Lazy ReadWriteService<GoodsType, Long> readWriteService) {
        this.goodsTypeDao = goodsTypeDao;
        this.readWriteService = readWriteService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsType> findAll() {
        return readWriteService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsType> findByGoodsSubcategoryId(Long id) {
        return goodsTypeDao.findByGoodsSubcategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsType> findById(Long id) {
        GoodsType goodsType = readWriteService.findById(id).orElseThrow(() ->
                new GoodsTypeNotFoundException(String.format("Goods type not found by id: %s", id)));
        return Optional.ofNullable(goodsType);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveOrUpdate(GoodsType goodsType) {

    }

}
