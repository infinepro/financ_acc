package ru.maksimka.jb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.maksimka.jb.bot.Bot;

import static ru.maksimka.jb.configurations.SpringContext.getContext;

public class MainBot {

    private static final Logger log = LogManager.getLogger("BOT INFO");

    public static void main(String[] args) {

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            log.info("Bot init....");
            botsApi.registerBot(getContext().getBean(Bot.class));
            log.info("Bot is working...and he is waiting requests...");

        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Bot doesn't working, where is problem padavan?");
        }
    }
}
