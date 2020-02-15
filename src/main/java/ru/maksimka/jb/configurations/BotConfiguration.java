package ru.maksimka.jb.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.maksimka.jb.bot.Bot;

import java.util.HashMap;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class BotConfiguration {

    @Value("${BOT_NAME}")
    private String botName;

    @Value("${BOT_TOKEN}")
    private String botToken;

    @Value("${PROXY_HOST}")
    private String proxyHost;

    @Value("${PROXY_PORT}")
    private int proxyPort;

    @Bean
    public DefaultBotOptions getBotOptions() {
        ApiContextInitializer.init();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }

    @Bean
    public Bot getBot(DefaultBotOptions botOptions) {
        return new Bot(botName, botToken, botOptions);
    }

    @Bean("responsesMap")
    public HashMap<String, String> getResponsesMap(){
        return new HashMap<String, String>() {{

            put("привет", "Привет друг");
            put("как дела", "Все отлично, а у тебя?))");


        }};
    }

    @Bean
    public SendMessage getSeandMessage(){
        return new SendMessage();
    }



}
