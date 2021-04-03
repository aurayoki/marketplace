package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AdvertisementAddingEnd implements AdvertisementGenerator {
    private AdvertisementService advertisementService;

    @Autowired
    public AdvertisementAddingEnd(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
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
        HashMap<Long, Advertisement> usersNewAdvertisement = (HashMap<Long, Advertisement>) objects[1];
        Advertisement advertisementDto = usersNewAdvertisement.get(chatId);
        advertisementDto.setPrice(Integer.parseInt((String) objects[0]));

        advertisementService.saveOrUpdate(advertisementDto); //  в данный момент при добавлении в базу дает ошибку

        builder.append("Объявление добавлено!").append("\n");

        currentGoodsStatus.remove(chatId);
        usersNewAdvertisement.remove(chatId);
    }

    @Override
    public String getMyCode() {
        return "6";
    }
}
