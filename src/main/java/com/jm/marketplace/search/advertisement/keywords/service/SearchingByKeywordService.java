package com.jm.marketplace.search.advertisement.keywords.service;

import com.jm.marketplace.model.Advertisement;

import java.util.List;

public interface SearchingByKeywordService {
    List<Advertisement> findByKeyword(String input);

    List<Advertisement> findByPrice(int number);
}
