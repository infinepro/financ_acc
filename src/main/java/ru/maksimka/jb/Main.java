package ru.maksimka.jb;

import org.springframework.context.ApplicationContext;
import ru.maksimka.jb.configurations.SpringContext;
import ru.maksimka.jb.console_views.WelcomeView;
import ru.maksimka.jb.services.MailService;

import static ru.maksimka.jb.configurations.SpringContext.*;

public class Main {

    public static void main(String[] args) {

        MailService mailService = getContext().getBean(MailService.class);
        mailService.sendSimpleMessage("maxxx_lp@mail.ru", "test send message", "just text for this message");

        //new WelcomeView().getWelcome();

    }
}
