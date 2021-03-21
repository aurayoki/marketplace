package com.jm.marketplace.service.goods;

import com.jm.marketplace.exception.GoodsCategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsCategory;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService<GoodsCategory, Long> {

    private final ReadWriteService<GoodsCategory, Long> readWriteService;

    @Autowired
    public GoodsCategoryServiceImpl(@Lazy ReadWriteService<GoodsCategory, Long> readWriteService) {
        this.readWriteService = readWriteService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsCategory> findAll() {
        return readWriteService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsCategory> findById(Long id) {
        GoodsCategory goodsCategory = readWriteService.findById(id).orElseThrow(() ->
                new GoodsCategoryNotFoundException(String.format("Goods category not found by id: %s", id)));
        return Optional.ofNullable(goodsCategory);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveOrUpdate(GoodsCategory goodsCategory) {

    }
}
