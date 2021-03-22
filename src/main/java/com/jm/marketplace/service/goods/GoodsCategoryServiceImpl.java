package com.jm.marketplace.service.goods;

import com.jm.marketplace.dao.GoodsCategoryDao;
import com.jm.marketplace.exception.GoodsCategoryNotFoundException;
import com.jm.marketplace.model.goods.GoodsCategory;
import com.jm.marketplace.service.general.ReadWriteService;
import com.jm.marketplace.service.general.ReadWriteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsCategoryServiceImpl extends ReadWriteServiceImpl<GoodsCategory, Long> implements GoodsCategoryService {

    private final GoodsCategoryDao goodsCategoryDao;

    @Autowired
    public GoodsCategoryServiceImpl(GoodsCategoryDao goodsCategoryDao) {
        super(goodsCategoryDao);
        this.goodsCategoryDao = goodsCategoryDao;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GoodsCategory> findById(Long id) {
        GoodsCategory goodsCategory = goodsCategoryDao.findById(id).orElseThrow(() ->
                new GoodsCategoryNotFoundException(String.format("Goods category not found by id: %s", id)));
        return Optional.ofNullable(goodsCategory);
    }
}
