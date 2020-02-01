package ru.maksimka.jb.console_views;

import org.springframework.stereotype.Component;

@Component
public class ForAuthorizedViewConsole extends ViewConsoleHelper {

    protected void showUserOptions() {

        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t>>> (1) Настройки пользователя \n");
        print("\t\t>>> (2) Настройки счетов и транзакций \n");
        print("\t\t>>> (3) Добавить новую транзакцию \n");


    }
}
