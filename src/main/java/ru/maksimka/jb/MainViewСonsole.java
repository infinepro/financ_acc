package ru.maksimka.jb;

import ru.maksimka.jb.console_views.StartViewConsole;

public class MainViewСonsole {

    public static void main(String[] args) {

       // MailService mailService = getContext().getBean(MailService.class);
       // mailService.sendSimpleMessage("maxxx_lp@mail.ru", "test send message", "just text for this message");
        new StartViewConsole().getWelcome();

    }
}
