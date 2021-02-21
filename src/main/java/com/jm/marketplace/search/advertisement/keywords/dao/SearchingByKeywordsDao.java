package com.jm.marketplace.search.advertisement.keywords.dao;

import com.jm.marketplace.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchingByKeywordsDao extends JpaRepository<Advertisement, Long> {
    @Query(value="select * from Advertisement a where a.description like %:keyword% or a.name like %:keyword%", nativeQuery=true)
    List<Advertisement> findByKeywords(@Param("keyword") String keyword);

    List<Advertisement> findByPrice(int price);
}
