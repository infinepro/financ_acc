package ru.maksimka.jb.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.maksimka.jb.ui.bot.Handler.*;

@Configuration
public class BotKeyboardsConfiguration {

    @Bean("start")
    public InlineKeyboardMarkup getStartButton() {
        InlineKeyboardMarkup keyButtonMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> listExit = new ArrayList<>();
        listExit.add(new InlineKeyboardButton()
                .setText("START")
                .setCallbackData(START));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(listExit);

        return keyButtonMarkup.setKeyboard(rowList);
    }

    @Bean("startKeyboard")
    public InlineKeyboardMarkup getStartKeyboard() {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> listExit = new ArrayList<>();
        listExit.add(new InlineKeyboardButton()
                    .setText("Выйти и попрощаться с ботом")
                    .setCallbackData(EXIT));

        List<InlineKeyboardButton> listButtons = new ArrayList<>();
        listButtons
                .add(new InlineKeyboardButton()
                        .setText("Зарегестрироваться")
                        .setCallbackData(REGISTER));

        listButtons
                .add(new InlineKeyboardButton()
                        .setText("Зачем?")
                        .setCallbackData(WHY));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(listButtons);
        rowList.add(listExit);

        return keyboardMarkup.setKeyboard(rowList);
    }
}
