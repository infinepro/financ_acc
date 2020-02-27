package ru.maksimka.jb.ui.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maksimka.jb.domain.services.StatusAuthorization;

import java.io.IOException;

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
            forUnauthorizedViewConsole.getAuthView(StatusAuthorization.REGISTERED);
        } else if (answer.equals("нет")) {
            forUnauthorizedViewConsole.getAuthView(StatusAuthorization.NOT_REGISTERED);
        } else {
            printErr("\tНекорректный ввод\n");
             getWelcome();
        }
    }

}
