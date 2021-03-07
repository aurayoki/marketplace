package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AdvertisementSelectGoodsType implements AdvertisementGenerator {
    private GoodsTypeService goodsTypeService;
    private GoodsSubcategoryService goodsSubcategoryService;

    @Autowired
    public AdvertisementSelectGoodsType(GoodsTypeService goodsTypeService, GoodsSubcategoryService goodsSubcategoryService) {
        this.goodsTypeService = goodsTypeService;
        this.goodsSubcategoryService = goodsSubcategoryService;
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
    public void execute(StringBuilder builder, HashMap<Long, Integer> currentGoodsStatus, Long chatId, Object... objects) {
        HashMap<Long, AdvertisementDto> usersNewAdvertisement = (HashMap<Long, AdvertisementDto>) objects[1];
        AdvertisementDto advertisementDto = usersNewAdvertisement.get(chatId);
        advertisementDto.setGoodsSubcategory(goodsSubcategoryService.findById(Long.parseLong((String) objects[0])));
        usersNewAdvertisement.put(chatId, advertisementDto);

        List<GoodsTypeDto> goodsTypeDtos = goodsTypeService.findByGoodsSubcategoryId(Long.parseLong((String) objects[0]));

        builder.append("Выберите тип товара (отправьте цифру)").append("\n");

        for (int i = 0; i < goodsTypeDtos.size(); i++) {
            builder.append((i + 1)).append(". ").append(goodsTypeDtos.get(i).getName()).append("\n");
        }

        currentGoodsStatus.put(chatId, currentGoodsStatus.get(chatId) + 1);
    }

    @Override
    public String getMyCode() {
        return "2";
    }
}
