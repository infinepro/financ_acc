package ru.maksimka.jb.console_views;

import ru.maksimka.jb.configurations.SpringContext;
import ru.maksimka.jb.services.AuthStatus;

import java.io.IOException;

public class WelcomeView extends ViewConsoleHelper {

    private ForUnauthorizedViewConsole forUnauthorizedViewConsole;

    public WelcomeView() {
        this.forUnauthorizedViewConsole = SpringContext.getContext().getBean(ForUnauthorizedViewConsole.class);
    }

    public void getWelcome() {
        String answer = "";

        printLine();
        print("\tДобро пожаловать к нам на пати, вы зарегистрированны? (да/нет)\n");
        print("\t>>>>>\t");

        try {
            answer = readStringFromConsole();
        } catch (IOException e) {
            printErr("\tпроизошла внтутрення ошибка");
            e.printStackTrace();
            getWelcome();
        }

        if (answer.equals("да")) {
            forUnauthorizedViewConsole.getAuthView(AuthStatus.REGISTERED);
        } else if (answer.equals("нет")) {
            forUnauthorizedViewConsole.getAuthView(AuthStatus.NOT_REGISTERED);
        } else {
            printErr("\tНекорректный ввод");
             getWelcome();
        }
    }

}
