package ru.maksimka.jb.console_views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maksimka.jb.services.AuthStatus;

import java.io.IOException;

import static ru.maksimka.jb.configurations.SpringContext.getContext;

@Component
public class MainViewConsole extends ViewConsoleHelper {

    private ForUnauthorizedViewConsole forUnauthorizedViewConsole;

    @Autowired
    public MainViewConsole(ForUnauthorizedViewConsole forUnauthorizedViewConsole) {
        this.forUnauthorizedViewConsole = forUnauthorizedViewConsole;
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
            printErr("\tНекорректный ввод\n");
             getWelcome();
        }
    }

}
