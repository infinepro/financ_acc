package ru.maksimka.jb.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private static final Logger log = LogManager.getLogger("BOT INFO");

    private final String botName;
    private final String botToken;

    @Autowired
    private BotMessagingDispatcher botMessagingDispatcher;

    public Bot(String botName, String botToken, DefaultBotOptions botOptions) {
        super(botOptions);
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            execute(botMessagingDispatcher.getSendMessageController(update));
            log.info("Бот ответил пользователю chatId: "+ update.getMessage().getChatId());
        } catch (TelegramApiException e) {
            log.error("Бот повалился ищи проблему в стеке");
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
