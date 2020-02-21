package ru.maksimka.jb.ui.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.ui.bot.Handlers.AllTheRestHandlers;
import ru.maksimka.jb.domain.dto.TelegramUserDto;

import java.util.HashMap;

import static ru.maksimka.jb.configurations.SpringContextSingleton.getContext;

@Service
public class BotMessagingDispatcher {

    private static final Logger LOGGER = LogManager.getLogger("BOT INFO");

    //пока не знаю другого варианта хранить авторизованных и нет юзеров, потом разберусь)
    private HashMap<Long, TelegramUserDto> usersList;

    @Qualifier("start")
    private final InlineKeyboardMarkup startKeyboard;

    public BotMessagingDispatcher(InlineKeyboardMarkup startKeyboard) {
        this.startKeyboard = startKeyboard;
        usersList = new HashMap<>();
    }
//todo: придётся переделывать на абилити бота
    private void userVerification(Update update) {

        long chatId;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chatId = update.getMessage().getChatId();
        }

    }

    public SendMessage getSendMessageController(Update update) {

        String handlerName;
        Handler handler;

        if (update.hasCallbackQuery()) {
            try {
                handlerName = update.getCallbackQuery().getData();
                handler = getContext().getBean(Handler.class, handlerName);
            } catch (BeansException e) {
                handler = getContext().getBean(AllTheRestHandlers.class);
            }
            return handler.execute(update);
        } else if (update.hasMessage()) {
            try {
                handlerName = update.getMessage().getText();
                handler = getContext().getBean(Handler.class, handlerName);
            } catch (BeansException e) {
                handler = getContext().getBean(AllTheRestHandlers.class);
            }
            return handler.execute(update);
        }
        return new SendMessage()
                .setText("Человек, ты прислал мне какую то ерунду...")
                .setChatId(update.getMessage().getChatId())
                .setReplyMarkup(startKeyboard);

    }

}
