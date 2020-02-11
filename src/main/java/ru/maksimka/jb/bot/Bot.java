package ru.maksimka.jb.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@PropertySource(value = "classpath:application.properties")
public class Bot extends TelegramLongPollingBot {

    private final String BOT_NAME = "finnAccBot";

    private final String BOT_TOKEN = "1083107893:AAGYbQa0vnLWynJFX9oIdyblRkxXPU_mRRI";

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        sendMessage.setText("Привет патамушта");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
