package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AdvertisementEnterDescriptionGoods implements AdvertisementGenerator {
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
        advertisementDto.setName((String) objects[0]);

        builder.append("Введите описание товара").append("\n");

        currentGoodsStatus.put(chatId, currentGoodsStatus.get(chatId) + 1);
    }

    @Override
    public String getMyCode() {
        return "4";
    }
}
