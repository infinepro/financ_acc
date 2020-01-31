package ru.maksimka.jb;


import ru.maksimka.jb.console_views.WelcomeView;

import static ru.maksimka.jb.configurations.SpringContext.*;

public class Main {

    public static void main(String[] args) {

        getContext().getBean(WelcomeView.class).getWelcome();

    }
}
