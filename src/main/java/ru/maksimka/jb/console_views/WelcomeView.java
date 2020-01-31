package ru.maksimka.jb.console_views;

import org.springframework.stereotype.Component;

@Component
public class WelcomeView extends AbstractViewConsole {

    public void getWelcome() {
        print("Добро пожаловать к нам на пати, вы зарегистрированны? (да/нет)");
    }

}
