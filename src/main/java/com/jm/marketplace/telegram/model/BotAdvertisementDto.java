package com.jm.marketplace.telegram.model;

import com.jm.marketplace.dto.UserDto;
import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BotAdvertisementDto {
    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Long getGoodsCategory() {
        return goodsCategory;
    }

    public Long getGoodsSubcategory() {
        return goodsSubcategory;
    }

    public Long getGoodsType() {
        return goodsType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoodsCategory(Long goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public void setGoodsSubcategory(Long goodsSubcategory) {
        this.goodsSubcategory = goodsSubcategory;
    }

    public void setGoodsType(Long goodsType) {
        this.goodsType = goodsType;
    }

    private String name;
    private Integer price;
    private String description;
    private Long goodsCategory;
    private Long goodsSubcategory;
    private Long goodsType;

    public List<String> emptyTextField() {
        List<String> emptyField = new ArrayList<>();
        if(name == null) {
            emptyField.add("Имя товара");
        }
        if(price == null) {
            emptyField.add("Цена товара");
        }
        if(description == null) {
            emptyField.add("Описание товара");
        }
        if(goodsCategory == null) {
            emptyField.add("Категория товара");
        }
        if(goodsSubcategory == null) {
            emptyField.add("Подкатегория товара");
        }
        if(goodsType == null) {
            emptyField.add("Тип товара");
        }
        return emptyField;
    }

    public List<String> emptyCallbackField() {
        List<String> emptyField = new ArrayList<>();
        if(name == null) {
            emptyField.add("ADD_NAME");
        }
        if(price == null) {
            emptyField.add("ADD_PRICE");
        }
        if(description == null) {
            emptyField.add("ADD_DESCRIPTION");
        }
        if(goodsCategory == null) {
            emptyField.add("ADD_CATEGORY");
        }
        if(goodsSubcategory == null) {
            emptyField.add("ADD_SUBCATEGORY");
        }
        if(goodsType == null) {
            emptyField.add("ADD_TYPE");
        }
        return emptyField;
    }

    @Override
    public String toString() {
        return "BotAdvertisementDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", goodsCategory=" + goodsCategory +
                ", goodsSubcategory=" + goodsSubcategory +
                ", goodsType=" + goodsType +
                '}';
    }
}
