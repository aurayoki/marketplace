package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.model.goods.GoodsCategory;
import com.jm.marketplace.model.goods.GoodsSubcategory;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AdvertisementSelectSubcategory implements AdvertisementGenerator {
    private final GoodsCategoryService<GoodsCategory, Long> goodsCategoryService;
    private final GoodsSubcategoryService<GoodsSubcategory, Long> goodsSubcategoryService;
    private final UserService<User, Long> userService;

    @Autowired
    public AdvertisementSelectSubcategory(GoodsCategoryService<GoodsCategory, Long> goodsCategoryService, GoodsSubcategoryService<GoodsSubcategory, Long> goodsSubcategoryService, UserService<User, Long> userService) {
        this.goodsCategoryService = goodsCategoryService;
        this.goodsSubcategoryService = goodsSubcategoryService;
        this.userService = userService;
    }

    /**
     * @param builder - объект в котором будут содержатся, собщения для пользователя в telegram bot
     * @param currentGoodsStatus - Статус добавления товара.
     * @param chatId - id чата в котором происходит добавление товара
     * @param objects - Используется в некоторых реализациях
*                objects[0] - Текст сообщения отправленное пользователем
*                      Например: Выбрать категорию товара, цифрой - 1 выбор категории транспорта.
*                objects[1] - HashMap<Long, AdvertisementDto> - используется для хранения
*                      промежуточного состояния товара во время добавления пользователем
     */
    @Override
    public void execute(StringBuilder builder, HashMap<Long, Integer> currentGoodsStatus, Long chatId, Object ...objects) {
        HashMap<Long, Advertisement> usersNewAdvertisement = (HashMap<Long, Advertisement>) objects[1];
        Advertisement advertisementDto = usersNewAdvertisement.get(chatId);
        advertisementDto.setUser(userService.findById(1L).get());
        advertisementDto.setGoodsCategory(goodsCategoryService.findById(Long.parseLong(String.valueOf(objects[0]))).get());
        usersNewAdvertisement.put(chatId, advertisementDto);

        List<GoodsSubcategory> goodsSubcategoryDtos = goodsSubcategoryService.findByGoodsCategoryId(Long.parseLong(String.valueOf(objects[0])));

        builder.append("Выберите подкатегорию (отправьте цифру)").append("\n");

        for (int i = 0; i < goodsSubcategoryDtos.size(); i++) {
            builder.append((i + 1)).append(". ").append(goodsSubcategoryDtos.get(i).getName()).append("\n");
        }

        currentGoodsStatus.put(chatId, currentGoodsStatus.get(chatId) + 1);
    }

    @Override
    public String getMyCode() {
        return "1";
    }
}
