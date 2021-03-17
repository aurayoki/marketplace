package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.service.goods.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AdvertisementEnterProductName implements AdvertisementGenerator {
    private GoodsTypeService goodsTypeService;

    @Autowired
    public AdvertisementEnterProductName(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
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
        advertisementDto.setGoodsType(goodsTypeService.findById(Long.parseLong((String) objects[0])));
        usersNewAdvertisement.put(chatId, advertisementDto);

        builder.append("Введите название товара").append("\n");

        currentGoodsStatus.put(chatId, currentGoodsStatus.get(chatId) + 1);
    }

    @Override
    public String getMyCode() {
        return "3";
    }
}
