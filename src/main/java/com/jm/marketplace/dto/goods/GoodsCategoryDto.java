package com.jm.marketplace.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCategoryDto {
    private Long id;
    private String name;
//    private Set<GoodsSubcategoryDto> goodsSubcategory;
}
