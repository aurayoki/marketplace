package com.jm.marketplace.service.telegram.buttons;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jm.marketplace.service.telegram.Bot.ADVERTISEMENTS_IN_PAGE;

@Component
public class TelegramBotInlineButtons {

    /**
     * @return Возвращает кнопки: 'Продавец', 'Город', 'Назад', при выводе информации о товаре.
     */
    public InlineKeyboardMarkup createButtonsOnProductPage() {
        String[] textButtons = {"Продавец", "Город"};
        String[] callbackData = {"pages_seller", "pages_city"};

        String[] textButtonBack = {"Назад"};
        String[] callbackDataBack = {"button_back_goods"};

        InlineKeyboardMarkup inlineKeyboardMarkup = createButtonsAccordingToSpecParam(textButtons, callbackData);
        InlineKeyboardMarkup inlineKeyboardMarkupSecond = createButtonsAccordingToSpecParam(textButtonBack, callbackDataBack);

        return combineSeveralInlineKeyboardMarkup(inlineKeyboardMarkup, inlineKeyboardMarkupSecond);
    }

    /**
     * @return Объединяет списки кнопок со всех InlineKeyboardMarkup в один.
     */
    private InlineKeyboardMarkup combineSeveralInlineKeyboardMarkup(InlineKeyboardMarkup ...inlineKeyboardMarkup) {
        InlineKeyboardMarkup inlineKeyboardMarkups = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<>();
        for (InlineKeyboardMarkup inline: inlineKeyboardMarkup) {
            inlineButtons.addAll(inline.getKeyboard());
        }

        inlineKeyboardMarkups.setKeyboard(inlineButtons);
        return inlineKeyboardMarkups;
    }

    /**
     * @return Возвращает клавиатуру с кнопками, собранные по заданным параметрам.
     */
    private InlineKeyboardMarkup createButtonsAccordingToSpecParam(String[] textButtons, String[] callbackData) {
        if (textButtons.length != callbackData.length) {
            throw new IllegalArgumentException(
                    "textButtons length != callbackData length: " + textButtons.length + " != " + callbackData.length);
        }

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        for (int i = 0; i < textButtons.length; i++) {
            buttons.add(getInlineKeyboardButton(textButtons[i], callbackData[i]));
        }

        return new InlineKeyboardMarkup(createNestedList(buttons));
    }

    /**
     * Создает вложенный список кнопок.
     * @param listButtons массив списков кнопок
     * @return Возвращает список из списков кнопок.
     */
    @SafeVarargs
    private List<List<InlineKeyboardButton>> createNestedList(List<InlineKeyboardButton> ...listButtons) {
        return new ArrayList<>(Arrays.asList(listButtons));
    }

    /**
     * @param buttonText Текст на кнопке.
     * @param callbackData Идентификатор кнопки, для определения конкретно нажатой кнопки.
     * @return Возвращает кнопку с заданными параметрами.
     */
    private InlineKeyboardButton getInlineKeyboardButton(String buttonText, String callbackData) {
        InlineKeyboardButton buttonBack = new InlineKeyboardButton(buttonText);
        buttonBack.setCallbackData(callbackData);

        return buttonBack;
    }

    /**
     * Возвращает кнопку назад, которая находится в блоке о продавце.
     */
    public InlineKeyboardMarkup getBackButtonInlineKeyboardMarkup() {
        String[] textButton = {"Назад"};
        String[] callbackData = {"button_back_seller"};

        return createButtonsAccordingToSpecParam(textButton, callbackData);
    }

    /**
     * Создает страницы виде кнопок.
    */
    private InlineKeyboardMarkup createInlineButtonsByNumberOfPages(int advertisementDtoSize) {
        int numberOfPages = (int) Math.ceil(advertisementDtoSize / (double) ADVERTISEMENTS_IN_PAGE);
        String[] textButton = new String[numberOfPages];
        String[] callbackData = new String[numberOfPages];
        for (int i = 1; i <= numberOfPages; i++) {
            textButton[i - 1] = String.valueOf(i);
            callbackData[i - 1] = "page_" + i;
        }

        return createButtonsAccordingToSpecParam(textButton, callbackData);
    }

    /**
     * Возвращает клавиатуру, из названия товаров, и страниц в виде кнопок.
     * @param advertisementDtoFullSize - количество товаров.
     * @param advertisementDtoListPage - товары конкретной страницы.
     */
    public InlineKeyboardMarkup createInlineButtonsKeyboardMarkupByGoods(int advertisementDtoFullSize, List<AdvertisementDto> advertisementDtoListPage) {
        String[] textButton = getProductNamesInArray(advertisementDtoListPage);
        String[] callbackData = getProductIdInArray(advertisementDtoListPage);
        addPrefixGoodsToId(callbackData);

        return combineSeveralInlineKeyboardMarkup(
                createButtonsAccordingToSpecParam(textButton, callbackData),
                createInlineButtonsByNumberOfPages(advertisementDtoFullSize));
    }

    private void addPrefixGoodsToId(String[] callbackData) {
        for (int i = 0; i < callbackData.length; i++) {
            callbackData[i] = "goods_" + callbackData[i];
        }
    }

    private String[] getProductIdInArray(List<AdvertisementDto> advertisementDtoList) {
        String[] productId = new String[advertisementDtoList.size()];
        for (int i = 0; i < advertisementDtoList.size(); i++) {
            productId[i] = String.valueOf(advertisementDtoList.get(i).getId());
        }
        return productId;
    }

    private String[] getProductNamesInArray(List<AdvertisementDto> advertisementDtoList) {
        String[] productNames = new String[advertisementDtoList.size()];
        for (int i = 0; i < advertisementDtoList.size(); i++) {
            productNames[i] = advertisementDtoList.get(i).getName();
        }
        return productNames;
    }
}
