package com.jm.marketplace.search.advertisement.keywords.service;

import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.search.advertisement.keywords.dao.SearchingByKeywordsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchingByKeywordServiceImp implements SearchingByKeywordService {
    @Autowired
    private SearchingByKeywordsDao keywordsDao;

    @Override
    public List<Advertisement> findByKeyword(String input) {
        return keywordsDao.findByKeywords(input);
    }

    @Override
    public List<Advertisement> findByPrice(int number) {
        return keywordsDao.findByPrice(number);
    }
}
