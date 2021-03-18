package com.jm.marketplace.service.telegram;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.model.Advertisement;
import com.jm.marketplace.model.User;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.telegram.advertisement.AdvertisementGenerator;
import com.jm.marketplace.service.telegram.buttons.TelegramBotInlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@PropertySource(value = "classpath:telegram.properties", encoding = "UTF-8")
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.button.list_advertisement}")
    private String listAdvertisement;

    @Value("${bot.button.add_advertisement}")
    private String addAdvertisement;

    @Value("${bot.button.list_advertisement_pagination}")
    private String listAdvertisementPagination;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private final List<KeyboardRow> keyboard = new ArrayList<>();
    private final TelegramBotInlineButtons inlineButtons = new TelegramBotInlineButtons();

    private AdvertisementService advertisementService;

    private HashMap<Long, Integer> currentGoodAddStatus = new HashMap<>();
    private HashMap<Long, AdvertisementDto> usersNewAdvertisement = new HashMap<>();
    private HashMap<String, AdvertisementGenerator> mapAdvertisementSelect = new HashMap<>();

    private int lastPageId;
    private int backGoodsId;


    public static final int ADVERTISEMENTS_IN_PAGE = 2;


    public void register(String code, AdvertisementGenerator advertisementGenerator) {
        mapAdvertisementSelect.put(code, advertisementGenerator);
    }

    @Autowired
    public void setAdvertisementService(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostConstruct
    public void init() {
        configKeyboard();
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    private List<Advertisement> filter() {
        return advertisementService.findAll(); //Заглушка для фильтра, сейчас возвращает все данные.
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Long currentChatId = null;
            String userName = null;
            String homePageMessage = "Товары";

            // если пользователь отправил сообщение боту
            if (update.getCallbackQuery() == null) {

                userName = update.getMessage().getFrom().getFirstName();
                currentChatId = update.getMessage().getChatId();
                String currentMessageText = update.getMessage().getText();

                refreshVariables(currentChatId, currentMessageText);

                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(currentChatId.toString());
                setButtons(sendMessage);

                if (currentGoodAddStatus.containsKey(currentChatId)) {
                    sendMessage.setText(addNewAdvertisement(currentChatId, currentMessageText));
                } else if (currentMessageText.equals("Список товаров постранично")) {
                    sendMessage.setReplyMarkup(getInlineButtonKeyboardMarkupByGoodsAndPages(1));
                    sendMessage.setText(homePageMessage);
                } else {
                    setMessage(sendMessage);
                }

                execute(sendMessage);

                // если пользователь нажал кнопку привязанную к сообщению
            } else {

                userName = update.getCallbackQuery().getFrom().getFirstName();
                currentChatId = update.getCallbackQuery().getMessage().getChatId();

                // То что будет выводится при переключении страниц page_
                if (checkCallbackQueryContains(update, "page_")) {
                    Integer currentPage = Integer.parseInt(update.getCallbackQuery().getData().substring(5));
                    lastPageId = currentPage;

                    updateChat(update, currentChatId, homePageMessage,
                            getInlineButtonKeyboardMarkupByGoodsAndPages(currentPage));

                } else if(checkCallbackQueryContains(update, "goods_")) {
                    Integer advertisementId = Integer.parseInt(update.getCallbackQuery().getData().substring(6));
                    backGoodsId = advertisementId;

                    String message = getInfoAboutAdvertisementByIdAdvertisement(advertisementId);
                    updateChat(update, currentChatId, message, inlineButtons.createButtonsOnProductPage());

                } else if (checkCallbackQueryContains(update, "button_back_goods")) {
                    Integer pageId = Math.max(1, lastPageId);
                    InlineKeyboardMarkup inlineKeyboard = getInlineButtonKeyboardMarkupByGoodsAndPages(pageId);

                    comeBack(update, currentChatId, homePageMessage, inlineKeyboard);

                } else if (checkCallbackQueryContains(update, "button_back_seller")) {
                    InlineKeyboardMarkup inlineKeyboard = inlineButtons.createButtonsOnProductPage();
                    String message = getInfoAboutAdvertisementByIdAdvertisement(Math.max(1, backGoodsId));

                    comeBack(update, currentChatId, message, inlineKeyboard);

                } else if (checkCallbackQueryContains(update, "pages_seller")) {
                    updateChat(update, currentChatId, getInfoTheSellerToByIdAdvertisement(backGoodsId), inlineButtons.getBackButtonInlineKeyboardMarkup());
                }
            }
            log.info("Send telegram message, username: {}", userName);
        } catch (Exception e) {
            log.warn("Error with send telegram message");
            e.printStackTrace();
        }
    }

    private void setMessage(SendMessage sendMessage) {

        List<Advertisement> advertisement = advertisementService.findAll();
        StringBuilder sb = new StringBuilder(advertisement.size());
        int count = 1;
        for (Advertisement advertisement1 : advertisement) {
            sb.append(count).append("\n");
            sb.append(advertisement1.getName()).append("\n");
            sb.append(advertisement1.getPrice()).append("\n");
            sb.append(advertisement1.getDescription()).append("\n");
            sb.append("-------------------");
            sb.append("\n");
            count++;
        }
        sendMessage.setText(sb.toString());
    }

    private void setButtons(SendMessage sendMessage) {
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(listAdvertisement);
        keyboardFirstRow.add(addAdvertisement);
        keyboardFirstRow.add(listAdvertisementPagination);
        keyboard.clear();
        keyboard.add(keyboardFirstRow);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }

    private void configKeyboard() {
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    /**
     * Проверка на наличие в update, значения из параметра value
     */
    private boolean checkCallbackQueryContains(Update update, String value) {
        return update.getCallbackQuery().getData().contains(value);
    }

    /**
     * Обновляет чат по заданным параметрам.
     */
    private void updateChat(Update update, Long chatId, String message, InlineKeyboardMarkup inlineKeyboard) throws TelegramApiException {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setChatId(chatId.toString());
        editMessageText.setReplyMarkup(inlineKeyboard);
        editMessageText.setText(message);
        execute(editMessageText);

        log.info(update.getCallbackQuery().getData());
        log.error(editMessageText.toString());
    }

    /**
     * Переход назад по кнопкам.
     */
    private void comeBack(Update update, Long chatId, String message, InlineKeyboardMarkup inlineKeyboard) throws TelegramApiException {
        updateChat(update, chatId, message, inlineKeyboard);
    }

    /**
     * Возвращает информацию об объявлении по id объявления.
     */
    private String getInfoAboutAdvertisementByIdAdvertisement(Integer advertisementId) {
        StringBuilder advertisementInfo = new StringBuilder();
        Advertisement advertisement = advertisementService.findById(advertisementId.longValue());
        advertisementInfo.append("Объявление: ").append(advertisement.getName()).append("\n");
        advertisementInfo.append("Цена: ").append(advertisement.getPrice()).append(" рублей").append("\n");
        advertisementInfo.append("Время публикаций объявления: ").append(advertisement.getPublication_date()).append("\n");
        advertisementInfo.append("Описание объявления: ").append(advertisement.getDescription()).append("\n");
        advertisementInfo.append("\n");
        return advertisementInfo.toString();
    }

    /**
     * Возвращает информацию о продавце по id объявления.
     */
    private String getInfoTheSellerToByIdAdvertisement(Integer advertisementId) {
        StringBuilder sellerInfo = new StringBuilder();
        Advertisement advertisement = advertisementService.findById(advertisementId.longValue());
        User user = advertisement.getUser();
        sellerInfo.append("Имя продавца: ").append(user.getFirstName()).append("\n");
        sellerInfo.append("Фамилие продавца: ").append(user.getLastName()).append("\n");
        sellerInfo.append("Email продавца: ").append(user.getEmail()).append("\n");
        sellerInfo.append("Номер телефона: ").append(user.getPhone()).append("\n");
        return sellerInfo.toString();
    }



    private String getAdvertisementTextForCurrentPage(int currentPage) {

        List<Advertisement> advertisements = getAdvertisementForCurrentPage(currentPage);

        StringBuilder sb = new StringBuilder();

        for (Advertisement advertisement : advertisements) {
            sb.append(advertisement.getName()).append("\n");
            sb.append(advertisement.getPrice()).append("\n");
            sb.append(advertisement.getDescription()).append("\n");
            sb.append(advertisement.getUser()).append("\n");
            sb.append("-------------------");
            sb.append("\n");
        }

        return sb.toString();

    }

    private Map<Advertisement, Integer> getAdvertisementPages() {

        List<Advertisement> advertisements = filter();
        Map<Advertisement, Integer> advertisementsEachPage = new HashMap<>();
        Integer pageNumber = 1;
        int count = 1;
        for (int i = 0; i < advertisements.size(); i++) {
            advertisementsEachPage.put(advertisements.get(i), pageNumber);
            if((i+1) % ADVERTISEMENTS_IN_PAGE == 0) {
                pageNumber++;
            }
        }
        return advertisementsEachPage;
    }

    private Integer getPageAdvertisementById(Integer id) {
        return getAdvertisementPages().get(advertisementService.findById(Long.valueOf(id)));
    }

    private List<Advertisement> getAdvertisementForCurrentPage(Integer currentPage) {
        List<Advertisement> advertisements = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisements;
    }

    /**
     * Возвращает клавиатуру, из названия товаров, и страниц в виде кнопок.
     */
    public InlineKeyboardMarkup getInlineButtonKeyboardMarkupByGoodsAndPages(Integer currentPage) {
        return inlineButtons.createInlineButtonsKeyboardMarkupByGoods(advertisementService.findAll().size(), getAdvertisementForCurrentPage(currentPage));
    }


    public void refreshVariables(Long currentChatId, String currentMessageText) {

        if (currentMessageText.equals("Добавить товар")) {
            currentGoodAddStatus.put(currentChatId, 0);
            usersNewAdvertisement.put(currentChatId, new AdvertisementDto());
        } else if (currentMessageText.equals(listAdvertisement) || currentMessageText.equals(listAdvertisementPagination) ) {
            if (currentGoodAddStatus.containsKey(currentChatId)) {
                currentGoodAddStatus.remove(currentChatId);
                usersNewAdvertisement.remove(currentChatId);
            }
        }
    }

    /**
     * Ищем в map, по id чата, соответствующий объект, и вызываем с заданными параметрами, поведение зависит от конкретного объекта.
     */
    public String addNewAdvertisement(Long currentChatId, String currentMessageText) {
        int currentGoodAddStatusId = currentGoodAddStatus.get(currentChatId);
        StringBuilder builder = new StringBuilder();

        mapAdvertisementSelect.get(String.valueOf(currentGoodAddStatusId))
                .execute(builder, currentGoodAddStatus, currentChatId, currentMessageText, usersNewAdvertisement);

        return builder.toString();
    }

}