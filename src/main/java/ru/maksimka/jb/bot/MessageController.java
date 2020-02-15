package ru.maksimka.jb.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;

@Service
public class MessageController {

    private static final Logger log = LogManager.getLogger("BOT INFO");

    @Qualifier("responsesMap")
    private final Map<String, String> mapResponses;

    @Qualifier("startKeyboard")
    private final InlineKeyboardMarkup startKeyboard;

    //@Autowired
    //private SendMessage sendMessage;

    public MessageController(InlineKeyboardMarkup startKeyboard, Map<String, String> mapResponses) {
        this.startKeyboard = startKeyboard;
        this.mapResponses = mapResponses;
    }

    public SendMessage getSendMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        StringBuilder incomeMessage = new StringBuilder(update
                .getMessage()
                .getText()
                .trim()
                .toLowerCase());

        log.info("Пришёл новый запрос боту, chatId:" + chatId
                + "\n------------------------] " + "Text: " + incomeMessage);

        SendMessage responseMessage = new SendMessage();
        responseMessage.setChatId(chatId);

        if (incomeMessage.toString().equals("/start")) {
            return responseMessage.setText("Приветствую тебя человек!")
                    .setReplyMarkup(startKeyboard);
        } else if (!mapResponses.containsKey(incomeMessage.toString())) {
            return responseMessage.setText("Я не понимаю....");
        } else return responseMessage;
    }
}
