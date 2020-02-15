package ru.maksimka.jb.bot.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.bot.Controller;

import java.util.Map;

@Service
public class AllTheRestControllers implements Controller {

    private static final Logger LOGGER = LogManager.getLogger("BOT INFO");

    @Qualifier("simpleAnswers")
    private Map<String, String> mapResponses;

    @Qualifier("startKeyboard")
    private InlineKeyboardMarkup startKeyboard;

    @Override
    public SendMessage execute(Update update) {

        long chatId;
        String message;
        if (update.hasCallbackQuery()) {
            message = new StringBuilder(update
                    .getCallbackQuery()
                    .getMessage()
                    .getText()
                    .trim()
                    .toLowerCase())
                    .toString();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            message = new StringBuilder(update
                    .getMessage()
                    .getText()
                    .trim()
                    .toLowerCase())
                    .toString();
            chatId = update.getMessage().getChatId();
        }

        LOGGER.info("Пришёл новый запрос боту, chatId:" + chatId
                + "\n------------------------] " + "Текст: " + message);

        if (message.equals(START)) {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText("Приветствую тебя человек!")
                    .setReplyMarkup(startKeyboard);

        } else if (!mapResponses.containsKey(message)) {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText("Я не понимаю....жми давай что нибудь уже")
                    .setReplyMarkup(startKeyboard);
        } else {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText(mapResponses.get(message));
        }
    }

}

