package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AdvertisementSelectCategory implements AdvertisementGenerator {
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    public AdvertisementSelectCategory(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    /**
     * @param builder - объект в котором будут содержатся, собщения для пользователя в telegram bot
     * @param currentGoodsStatus - Статус добавления товара.
     * @param chatId - id чата в котором происходит добавление товара
     * @param objects - Используется в некоторых реализациях
*                objects[0] - Текст сообщения отправленное пользователем
*                      Например: Выбрать категорию товара в начале, цифрой - 1 выбор категории транспорта.
*                objects[1] - HashMap<Long, AdvertisementDto> - используется для хранения
*                      промежуточного состояния товара во время добавления пользователем
     */
    @Override
    public void execute(StringBuilder builder, HashMap<Long, Integer> currentGoodsStatus, Long chatId, Object ...objects) {
        builder.append("Выберите категорию (отправьте цифру)").append("\n");

        List<GoodsCategoryDto> goodsCategoryDtos = goodsCategoryService.findAll();

        for (int i = 0; i < goodsCategoryDtos.size(); i++) {
            builder.append((i + 1)).append(". ").append(goodsCategoryDtos.get(i).getName()).append("\n");
        }

        currentGoodsStatus.put(chatId, currentGoodsStatus.get(chatId) + 1);
    }

    @Override
    public String getMyCode() {
        return "0";
    }
}
