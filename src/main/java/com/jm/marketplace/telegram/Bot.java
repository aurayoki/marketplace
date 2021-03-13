package com.jm.marketplace.telegram;

import com.jm.marketplace.dto.goods.AdvertisementDto;
import com.jm.marketplace.dto.goods.GoodsCategoryDto;
import com.jm.marketplace.dto.goods.GoodsSubcategoryDto;
import com.jm.marketplace.dto.goods.GoodsTypeDto;
import com.jm.marketplace.service.advertisement.AdvertisementService;
import com.jm.marketplace.service.goods.GoodsCategoryService;
import com.jm.marketplace.service.goods.GoodsSubcategoryService;
import com.jm.marketplace.service.goods.GoodsTypeService;
import com.jm.marketplace.service.user.UserService;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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

    private AdvertisementService advertisementService;

    private HashMap<Long, Integer> currentGoodAddStatus = new HashMap<>();
    private HashMap<Long, AdvertisementDto> usersNewAdvertisement = new HashMap<>();

    private GoodsCategoryService goodsCategoryService;
    private GoodsSubcategoryService goodsSubcategoryService;
    private GoodsTypeService goodsTypeService;

    private UserService userService;

    public static final int ADVERTISEMENTS_IN_PAGE = 2;

    private InlineKeyboardMarkup inlineKeyboardMarkup = null;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGoodsCategoryService(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    @Autowired
    public void setGoodsSubcategoryService(GoodsSubcategoryService goodsSubcategoryService) {
        this.goodsSubcategoryService = goodsSubcategoryService;
    }

    @Autowired
    public void setGoodsTypeService(GoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
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

    private List<AdvertisementDto> filter() {
        return advertisementService.findAll(); //Заглушка для фильтра, сейчас возвращает все данные.
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {

            SendMessage sendMessage = null;
            Long currentChatId = null;
            String currentMessageText = null;
            String userName = null;

            // если пользователь отправил сообщение боту
            if (update.getCallbackQuery() == null) {

                userName = update.getMessage().getFrom().getUserName();
                currentChatId = update.getMessage().getChatId();
                currentMessageText = update.getMessage().getText();

                refreshVariables(currentChatId, currentMessageText);

                sendMessage = new SendMessage();
                sendMessage.setChatId(currentChatId.toString());
                setButtons(sendMessage);

                if (currentGoodAddStatus.containsKey(currentChatId)) {
                    sendMessage.setText(addNewAdvertisement(currentChatId, currentMessageText));
                } else if (currentMessageText.equals("Список товаров постранично")) {
                    sendMessage.setReplyMarkup(getInlineButtonsPagination(1));
                    sendMessage.setText(getAdvertisementTextForCurrentPage(1));
                } else {
                    setMessage(sendMessage);
                }

                execute(sendMessage);

                // если пользователь нажал кнопку привязанную к сообщению
            } else {

                userName = update.getCallbackQuery().getFrom().getUserName();
                currentChatId = update.getCallbackQuery().getMessage().getChatId();

                if (update.getCallbackQuery().getData().contains("page_")) {

                    Integer currentPage = Integer.parseInt(update.getCallbackQuery().getData().substring(5));

                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    editMessageText.setText(getAdvertisementTextForCurrentPage(currentPage));
                    editMessageText.setReplyMarkup(getInlineButtonsPagination(currentPage));
                    editMessageText.setChatId(currentChatId.toString());
                    execute(editMessageText);
                }
                else if(update.getCallbackQuery().getData().contains("goods_")) {
                    Integer advertisementId = Integer.parseInt(update.getCallbackQuery().getData().substring(6));
                    EditMessageText editMessageText = new EditMessageText();
                    editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    editMessageText.setChatId(currentChatId.toString());
                    editMessageText.setReplyMarkup(getInlineButtonsPagination(getPageAdvertisementById(advertisementId)));
                    editMessageText.setText(getAdvertisementText(advertisementId));
                    execute(editMessageText);
                }

            }

            log.info("Send telegram message, username: {}", userName);
        } catch (Exception e) {
            log.warn("Error with send telegram message");
            e.printStackTrace();
        }
    }

    private void setMessage(SendMessage sendMessage) {

        List<AdvertisementDto> advertisementDtos = advertisementService.findAll();
        StringBuilder sb = new StringBuilder(advertisementDtos.size());
        int count = 1;
        for (AdvertisementDto advertisementDto : advertisementDtos) {
            sb.append(count).append("\n");
            sb.append(advertisementDto.getName()).append("\n");
            sb.append(advertisementDto.getPrice()).append("\n");
            sb.append(advertisementDto.getDescription()).append("\n");
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

    private String getAdvertisementText(Integer advertisementId) {
        StringBuilder sb = new StringBuilder();
        AdvertisementDto advertisement = advertisementService.findById(advertisementId.longValue());
        sb.append(advertisement.getName()).append("\n");
        sb.append(advertisement.getPrice()).append("\n");
        sb.append(advertisement.getPublication_date()).append("\n");
        sb.append(advertisement.getDescription()).append("\n");
        sb.append(advertisement.getUser()).append("\n");
        sb.append("\n");
        return sb.toString();
    }

    private String getAdvertisementTextForCurrentPage(int currentPage) {

        List<AdvertisementDto> advertisementDtos = getAdvertisementForCurrentPage(currentPage);

        StringBuilder sb = new StringBuilder();

        for (AdvertisementDto advertisementDto : advertisementDtos) {
            sb.append(advertisementDto.getName()).append("\n");
            sb.append(advertisementDto.getPrice()).append("\n");
            sb.append(advertisementDto.getDescription()).append("\n");
            sb.append(advertisementDto.getUser()).append("\n");
            sb.append("-------------------");
            sb.append("\n");
        }

        return sb.toString();

    }

    private Map<AdvertisementDto, Integer> getAdvertisementPages() {

        List<AdvertisementDto> advertisementDtos = filter();
        Map<AdvertisementDto, Integer> advertisementsEachPage = new HashMap<>();
        Integer pageNumber = 1;
        int count = 1;
        for (int i = 0; i < advertisementDtos.size(); i++) {
            advertisementsEachPage.put(advertisementDtos.get(i), pageNumber);
            if((i+1) % ADVERTISEMENTS_IN_PAGE == 0) {
                pageNumber++;
            }
        }
        return advertisementsEachPage;
    }

    private Integer getPageAdvertisementById(Integer id) {
        return getAdvertisementPages().get(advertisementService.findById(Long.valueOf(id)));
    }

    private  List<AdvertisementDto> getAdvertisementForCurrentPage(Integer currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementPages()
                .entrySet()
                .stream()
                .filter(advertisement ->advertisement.getValue() == currentPage)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return advertisementDtos;
    }


    private List<InlineKeyboardButton> getInlineKeyboardButtonsAdvertosementForCurrentPage(Integer currentPage) {
        List<AdvertisementDto> advertisementDtos = getAdvertisementForCurrentPage(currentPage);
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        for(AdvertisementDto advertisementDto : advertisementDtos) {
            InlineKeyboardButton advertisementButton = new InlineKeyboardButton();
            advertisementButton.setText(advertisementDto.getName());
            advertisementButton.setCallbackData("goods_" + advertisementDto.getId());
            keyboardButtonsRow.add(advertisementButton);
       }

        return keyboardButtonsRow;
    }

    private List<InlineKeyboardButton> getInlineKeyboardButtonPagination() {
        List<AdvertisementDto> advertisementDtos = advertisementService.findAll();
        int pagesCount = (int) Math.ceil(advertisementDtos.size() / (double) ADVERTISEMENTS_IN_PAGE);
        List<InlineKeyboardButton> keyboardButtonsRowPageNamber = new ArrayList<>();

        for (int i = 1; i <= pagesCount; i++) {
            InlineKeyboardButton pageNumberButton = new InlineKeyboardButton();
            pageNumberButton.setText(String.valueOf(i));
            pageNumberButton.setCallbackData("page_" + (i));
            keyboardButtonsRowPageNamber.add(pageNumberButton);
        }
       return keyboardButtonsRowPageNamber;
    }

    private InlineKeyboardMarkup getInlineButtonsPagination(Integer currentPage) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getInlineKeyboardButtonsAdvertosementForCurrentPage(currentPage));
        rowList.add(getInlineKeyboardButtonPagination());
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
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

    public String addNewAdvertisement(Long currentChatId, String currentMessageText) {

        int currentGoodAddStatusId = currentGoodAddStatus.get(currentChatId);

        StringBuilder stringBuilder = new StringBuilder();

        if (currentGoodAddStatusId == 0) {

            stringBuilder.append("Выберите категорию (отправьте цифру)").append("\n");

            List<GoodsCategoryDto> goodsCategoryDtos = goodsCategoryService.findAll();

            for (int i = 0; i < goodsCategoryDtos.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(goodsCategoryDtos.get(i).getName()).append("\n");
            }

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 1) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setUser(userService.findById(1L));
            advertisementDto.setGoodsCategory(goodsCategoryService.findById(Long.parseLong(currentMessageText)));
            usersNewAdvertisement.put(currentChatId, advertisementDto);

            List<GoodsSubcategoryDto> goodsSubcategoryDtos = goodsSubcategoryService.findByGoodsCategoryId(Long.parseLong(currentMessageText));

            stringBuilder.append("Выберите подкатегорию (отправьте цифру)").append("\n");

            for (int i = 0; i < goodsSubcategoryDtos.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(goodsSubcategoryDtos.get(i).getName()).append("\n");
            }

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 2) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setGoodsSubcategory(goodsSubcategoryService.findById(Long.parseLong(currentMessageText)));
            usersNewAdvertisement.put(currentChatId, advertisementDto);

            List<GoodsTypeDto> goodsTypeDtos = goodsTypeService.findByGoodsSubcategoryId(Long.parseLong(currentMessageText));

            stringBuilder.append("Выберите тип товара (отправьте цифру)").append("\n");

            for (int i = 0; i < goodsTypeDtos.size(); i++) {
                stringBuilder.append((i + 1)).append(". ").append(goodsTypeDtos.get(i).getName()).append("\n");
            }

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 3) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setGoodsType(goodsTypeService.findById(Long.parseLong(currentMessageText)));
            usersNewAdvertisement.put(currentChatId, advertisementDto);

            stringBuilder.append("Введите название товара").append("\n");

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 4) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setName(currentMessageText);

            stringBuilder.append("Введите описание товара").append("\n");

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 5) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setDescription(currentMessageText);
            usersNewAdvertisement.put(currentChatId, advertisementDto);

            stringBuilder.append("Введите цену товара").append("\n");

            currentGoodAddStatus.put(currentChatId, currentGoodAddStatusId + 1);

        } else if (currentGoodAddStatusId == 6) {

            AdvertisementDto advertisementDto = usersNewAdvertisement.get(currentChatId);
            advertisementDto.setPrice(Integer.parseInt(currentMessageText));

            advertisementService.saveOrUpdate(advertisementDto); //  в данный момент при добавлении в базу дает ошибку

            stringBuilder.append("Объявление добавлено!").append("\n");

            currentGoodAddStatus.remove(currentChatId);
            usersNewAdvertisement.remove(currentChatId);

        }
        return stringBuilder.toString();
    }

}