package com.jm.marketplace.service.telegram.advertisement;

import com.jm.marketplace.service.telegram.Bot;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public interface AdvertisementGenerator {
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
    void execute(StringBuilder builder, HashMap<Long, Integer> currentGoodsStatus, Long chatId, Object ...objects);
    String getMyCode();

    @Autowired
    default void registerMySelf(Bot bot) {
        bot.register(getMyCode(), this);
    }
}
