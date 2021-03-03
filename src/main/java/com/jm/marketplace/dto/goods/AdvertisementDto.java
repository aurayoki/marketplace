package com.jm.marketplace.dto.goods;

import com.jm.marketplace.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private UserDto user;
    private String image;
    private LocalDateTime publication_date;
    private Boolean active;
    private Boolean expired;
    private Boolean banned;
    private GoodsCategoryDto goodsCategory;
    private GoodsSubcategoryDto goodsSubcategory;
    private GoodsTypeDto goodsType;
}
