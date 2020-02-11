package ru.maksimka.jb;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.maksimka.jb.bot.Bot;

public class MainBot {
    public static void main(String[] args) {

        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("https.proxyHost", "https://exp.proxydigitalresistance.dog");
        System.setProperty("https.proxyPort", "443");

//        System.getProperties().put( "proxySet", "true" );
  //      System.getProperties().put( "socksProxyHost", "https://exp.proxydigitalresistance.dog" );
    //    System.getProperties().put( "socksProxyPort", "443" );

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try{
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
