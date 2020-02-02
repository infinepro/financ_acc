package ru.maksimka.jb.console_views;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.services.Services;

import java.io.IOException;

@Component
public class ForAuthorizedViewConsole extends ViewConsoleHelper {

    private Services serviceUsers;

    protected void showUserOptions(Services serviceUsers) {
        this.serviceUsers = serviceUsers;

        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t>>> (0) Выход из программы \n");
        print("\t\t>>> (1) Настройки пользователя \n");
        print("\t\t>>> (2) Действия со счетами \n");
        print("\t\t>>> (3) Действия с транзакциями \n");
        print("\t\t>>> (4) ПОКУПКА \n");
        print("\t\t>>> (5) ПОПОЛНЕНИЕ \n");
        print("\t\t>>>>> ");

        try {
            int answer = readNumberFromConsole();
            switch (answer) {
                case 0: {
                    System.exit(1);
                }

                case 1: {
                    getSettingUser();
                }

                case 2: {
                    getAccountOperations();
                }

                case 3: {
                    break;
                }

                case 4: {

                }
            }

        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод");
            showUserOptions(this.serviceUsers);
        }


    }

    private void getSettingUser() {

        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t>>> (0) ...назад \n");
        print("\t\t>>> (1) Изменить пароль \n");
        print("\t\t>>> (2) Изменить емаил \n");
        print("\t\t>>> (3) Удалить пользователя \n");
        print("\t\t>>>>> ");

        try {
            int answer = readNumberFromConsole();
            switch (answer) {
                case 0: {
                    showUserOptions(this.serviceUsers);
                }

                case 1: {
                   printLine();
                   print("\tВведите новый пароль");
                   print("\t>>>>>  ");
                   String newPassword = readStringFromConsole();
                   serviceUsers.changePassword(newPassword);
                   print("\tПароль успешно изменён");
                   getSettingUser();
                   break;
                }

                case 2: {
                    printLine();
                    print("\tВведите новый email");
                    print("\t>>>>>  ");
                    String newEmail = readStringFromConsole();
                    serviceUsers.changeEmail(newEmail);
                    print("\tПочта успешно изменена");
                    getSettingUser();
                    break;
                }

                case 3: {
                    printLine();
                    print("\tВы уверены что хотите удалить пользователя и связанные с ним данные?");
                    print("\t(1) - удалить пользователя");
                    print("\t>>>>>  ");
                    int resp = readNumberFromConsole();
                    if (resp == 1) {
                        serviceUsers.deleteUser();
                    }
                    printLine();
                    print("Пользователь удалён");
                    new WelcomeView().getWelcome();
                    break;
                }

                default: throw new NumberFormatException();
            }

        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод");
            getSettingUser();
        } catch (NotAuthorizedException e) {
            printErr("\tПользователь не авторизован");
        }
    }

    private void getAccountOperations() {

        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t>>> (0) ...назад \n");
        print("\t\t>>> (1) Добавить новый счет \n");
        print("\t\t>>> (2) Добавить новый тип счёта \n");
        print("\t\t>>> (3) Удалить счет \n");
        print("\t\t>>> (4) Удалить тип счета\n");
        print("\t\t>>>>> ");

        try {
            int answer = readNumberFromConsole();
            switch (answer) {
                case 0: {
                    showUserOptions(this.serviceUsers);
                }

                case 1: {
                    serviceUsers

                }

                case 2: {

                }

                case 3: {
                    break;
                }

                case 4: {

                }
            }

        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод");
            showUserOptions(this.serviceUsers);
        }



    }
}
