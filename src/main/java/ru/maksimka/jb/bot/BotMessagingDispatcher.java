package ru.maksimka.jb.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.bot.Controllers.AllTheRestHandler;

import static ru.maksimka.jb.configurations.SpringContext.getContext;

@Service
public class BotMessagingDispatcher {

    private static final Logger LOGGER = LogManager.getLogger("BOT INFO");

    @Qualifier("start")
    private final InlineKeyboardMarkup startKeyboard;

    public BotMessagingDispatcher(InlineKeyboardMarkup startKeyboard) {
        this.startKeyboard = startKeyboard;
    }

    public SendMessage getSendMessageController(Update update) {

        String handlerName;
        Handler handler;

        if (update.hasCallbackQuery()) {
            try {
                handlerName = update.getCallbackQuery().getData();
                handler = getContext().getBean(Handler.class, handlerName);
            } catch (BeansException e) {
                handler = getContext().getBean(AllTheRestHandler.class);
            } return handler.execute(update);
        } else if (update.hasMessage()) {
            try {
                handlerName = update.getMessage().getText();
                handler = getContext().getBean(Handler.class, handlerName);
            } catch (BeansException e) {
                handler = getContext().getBean(AllTheRestHandler.class);
            } return handler.execute(update);
        }
        return new SendMessage()
                .setText("Человек, ты прислал мне какую то ерунду...")
                .setChatId(update.getMessage().getChatId())
                .setReplyMarkup(startKeyboard);

    }

}
