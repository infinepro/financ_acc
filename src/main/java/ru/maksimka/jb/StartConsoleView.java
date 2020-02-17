package ru.maksimka.jb;

import ru.maksimka.jb.console_views.MainViewConsole;

import static ru.maksimka.jb.configurations.SpringContext.*;

public class StartConsoleView {

    public static void main(String[] args) {

       // MailService mailService = getContext().getBean(MailService.class);
       // mailService.sendSimpleMessage("maxxx_lp@mail.ru", "test send message", "just text for this message");

        getContext().getBean(MainViewConsole.class).getWelcome();


    }
}
