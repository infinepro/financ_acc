package ru.maksimka.jb.ui.bot.Handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.ui.bot.Handler;

import java.util.Map;

import static ru.maksimka.jb.configurations.SpringContextSingleton.getContext;

@Service
public class AllTheRestHandlers implements Handler {

    private static final Logger LOGGER = LogManager.getLogger("BOT INFO");

    @Autowired
    @Qualifier("simpleAnswers")
    private Map<String, String> mapResponses;

    @Autowired
    @Qualifier("start")
    private InlineKeyboardMarkup keyboard;

    @Override
    public SendMessage execute(Update update) {

        long chatId;
        String message;
        if (update.hasCallbackQuery()) {
            message = update
                    .getCallbackQuery()
                    .getData()
                    .trim()
                    .toLowerCase();

            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            message = update
                    .getMessage()
                    .getText()
                    .trim()
                    .toLowerCase();
            chatId = update.getMessage().getChatId();
        }

        LOGGER.info("Пришёл новый запрос боту, chatId:" + chatId
                + "\n------------------------] " + "Текст: " + message);

        if (message.equals(START)) {
            keyboard = getContext().getBean( "startKeyboard", InlineKeyboardMarkup.class);
            return new SendMessage()
                    .setChatId(chatId)
                    .setText("Окей, жмакай кнопку для дальнейших действий! 👇")
                    .setReplyMarkup(keyboard);
        } else if (message.equals(EXIT)){
            return new SendMessage()
                    .setChatId(chatId)
                    .setText("Пока человек...ты это, заходи если что! 👋");
        }else if (!mapResponses.containsKey(message)) {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText("Я не понимаю....жми давай что нибудь уже")
                    .setReplyMarkup(keyboard);
        } else {
            return new SendMessage()
                    .setChatId(chatId)
                    .setText(mapResponses.get(message))
                    .setReplyMarkup(keyboard);
        }
    }
}

