package com.jm.marketplace.model;

import com.jm.marketplace.model.goods.GoodsCategory;
import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.model.goods.GoodsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "advertisement")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "publication_date_time")
    private LocalDateTime publication_date;

    @Column(name = "banned")
    private Boolean banned;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_category_id", nullable = false)
    private GoodsCategory goodsCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_subcategory_id", nullable = false)
    private GoodsSubcategory goodsSubcategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_type_id", nullable = false)
    private GoodsType goodsType;

    public Advertisement(String name,
                         Integer price,
                         String description,
                         GoodsCategory goodsCategory,
                         GoodsSubcategory goodsSubcategory,
                         GoodsType goodsType,
                         User user) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.goodsCategory = goodsCategory;
        this.goodsSubcategory = goodsSubcategory;
        this.goodsType = goodsType;
        this.user = user;
    }
}
