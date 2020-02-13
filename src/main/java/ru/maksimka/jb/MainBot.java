package ru.maksimka.jb;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import ru.maksimka.jb.bot.Bot;

public class MainBot {

    private static final Logger log = Logger.getLogger(MainBot.class);

    public static void main(String[] args) {
//TODO: настроить прокси
        String PROXY_HOST = "172.65.38.80";
        Integer PROXY_PORT = 443;
        //"994dc138e01718f07f39cf78f418a4770";

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        ApiContextInitializer.init();
        Bot bot = new Bot(botOptions);
        bot.botConnect();


//        System.getProperties().put( "proxySet", "true" );
        //      System.getProperties().put( "socksProxyHost", "https://exp.proxydigitalresistance.dog" );
        //    System.getProperties().put( "socksProxyPort", "443" );

    }
}
