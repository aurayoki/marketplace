package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsSubcategoryDao;
import com.jm.marketplace.exception.GoodsSubcategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.service.general.ReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsSubcategoryServiceImpl implements GoodsSubcategoryService<GoodsSubcategory, Long> {

    private final GoodsSubcategoryDao goodsSubcategoryDao;
    private final ReadWriteService<GoodsSubcategory, Long> readWriteService;

    @Autowired
    public GoodsSubcategoryServiceImpl(GoodsSubcategoryDao goodsSubcategoryDao, @Lazy ReadWriteService<GoodsSubcategory, Long> readWriteService) {
        this.goodsSubcategoryDao = goodsSubcategoryDao;
        this.readWriteService = readWriteService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsSubcategory> findAll() {
        return readWriteService.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GoodsSubcategory> findByGoodsCategoryId(Long id) {
        return goodsSubcategoryDao.findByGoodsCategoryId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsSubcategory> findById(Long id) {
        GoodsSubcategory goodsSubcategory = readWriteService.findById(id).orElseThrow(() ->
                new GoodsSubcategoryNotFoundException(String.format("Goods subcategory not found by id: %s", id)));
        return Optional.ofNullable(goodsSubcategory);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void saveOrUpdate(GoodsSubcategory goodsSubcategory) {

    }

}
