package ru.maksimka.jb.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Controller {
    String START = "/start";
    String REGISTER = "!register";
    String WHY = "!why";
    String EXIT = "!exit";
    String LOGIN = "!login";

    SendMessage execute(Update update);
}
