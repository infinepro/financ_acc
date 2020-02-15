package ru.maksimka.jb.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.bot.Controllers.AllTheRestControllers;

import java.util.Map;

import static ru.maksimka.jb.bot.Controller.REGISTER;
import static ru.maksimka.jb.configurations.SpringContext.getContext;

@Service
public class BotMessagingDispatcher {

    private static final Logger LOGGER = LogManager.getLogger("BOT INFO");

    @Qualifier("simpleAnswers")
    private final Map<String, String> mapResponses;

    @Qualifier("startKeyboard")
    private final InlineKeyboardMarkup startKeyboard;

    public BotMessagingDispatcher(InlineKeyboardMarkup startKeyboard, Map<String, String> mapResponses) {
        this.startKeyboard = startKeyboard;
        this.mapResponses = mapResponses;
    }

    public SendMessage getSendMessageController(Update update) {

        String nameController;
        Controller controller;
        String message;

        if (update.hasCallbackQuery()) {
            try {
                nameController = update.getCallbackQuery().getData();
                controller = getContext().getBean(Controller.class, nameController);
            } catch (BeansException e) {
                controller = getContext().getBean(AllTheRestControllers.class);
            } return controller.execute(update);
        } else if (update.hasMessage()) {
            try {
                nameController = update.getMessage().getText();
                controller = getContext().getBean(Controller.class, nameController);
            } catch (BeansException e) {
                controller = getContext().getBean(AllTheRestControllers.class);
            } return controller.execute(update);
        }
        return new SendMessage()
                .setText("Человек, ты прислал мне какую то ерунду...")
                .setChatId(update.getMessage().getChatId())
                .setReplyMarkup(startKeyboard);

    }

}
