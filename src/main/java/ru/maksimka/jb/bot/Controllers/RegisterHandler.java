package ru.maksimka.jb.bot.Controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.maksimka.jb.bot.Handler;

import static ru.maksimka.jb.bot.Handler.REGISTER;

@Service(REGISTER)
public class RegisterHandler implements Handler {

    @Qualifier("startKeyboard")
    InlineKeyboardMarkup Keyboard;

    @Override
    public SendMessage execute(Update update) {
        return null;
    }



}
