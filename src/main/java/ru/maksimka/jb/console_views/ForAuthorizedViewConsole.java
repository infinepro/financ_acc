package ru.maksimka.jb.console_views;

import org.hibernate.QueryException;
import org.springframework.stereotype.Component;
import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.dto.TransactionCategoryDto;
import ru.maksimka.jb.dto.TransactionDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.InvalidSummException;
import ru.maksimka.jb.exceptions.NotAuthorizedException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.services.Services;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

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
        print("\t\t>>> (4) Перевод средств со счета на счет\n");
        print("\t\t>>> (5) ПОКУПКА \n");
        print("\t\t>>> (6) ПОПОЛНЕНИЕ \n");
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
                    getTransactionSettings();
                }

                case 4: {
                    try {
                        makeTransactionBetweenAccounts();
                    } catch (NotAuthorizedException e) {
                        e.printStackTrace();
                    } catch (InvalidSummException e) {
                        printLine();
                        printErr("Вы ввели сумму, " +
                                "превышающую наличие средств на счете для списания");
                    }
                    showUserOptions(this.serviceUsers);
                }

                case 5: {
                    try {
                        makePurchase();
                    } catch (NotAuthorizedException e) {
                        e.printStackTrace();
                    } catch (InvalidSummException e) {
                        printErr("\tНе хватает средств на счёте!\n");
                        showUserOptions(this.serviceUsers);
                    }
                    showUserOptions(this.serviceUsers);
                }

                case 6: {

                }
            }

        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод");
            showUserOptions(this.serviceUsers);
        }
    }

    //
    private void replenishBalance() throws NotAuthorizedException, IOException {
        printLine();
        List<AccountDto> listAccs = serviceUsers.getAllAccounts();
        printListUserAccounts(listAccs);
        print("\tВыберите какой счет пополнить:\n");
        print("\t>>>>>  ");
        int typeAcId = listAccs.get(readNumberFromConsole() - 1).getId();
        print("\tВведите сумму пополнение:\n");
        print("\t>>>>>  ");
        BigDecimal sum = readSumFromConsole();
        try {
            serviceUsers.addNewTransaction(typeAcId, sum);
        } catch (RecordNotFoundException e) {
            printErr("\tНе найден счет для пополнения, идите к админу\n");
            e.printStackTrace();
            printLine();
            return;
        }

        printLine();
        print("\tСумма списана, добавлена транзакция.\n");
    }

    private void makePurchase() throws IOException, NotAuthorizedException, InvalidSummException{
        printLine();
        List<TransactionCategoryDto> listTransCat = serviceUsers.getAllTransactionCategory();
        printListTransactionCategories(listTransCat);
        print("\tВыберите тип транзакции (покупки):\n");
        print("\t>>>>>  ");
        int typeTrId = listTransCat.get(readNumberFromConsole() - 1).getId();
        List<AccountDto> listAccs = serviceUsers.getAllAccounts();
        printListUserAccounts(listAccs);
        print("\tВыберите с какого счета покупка:\n");
        print("\t>>>>>  ");
        int typeAcId = listAccs.get(readNumberFromConsole() - 1).getId();
        print("\tВведите сумму покупки:\n");
        print("\t>>>>>  ");
        BigDecimal sum = readSumFromConsole();
        try {
            serviceUsers.addNewTransaction(typeTrId, typeAcId, sum.negate());
        } catch (RecordNotFoundException e) {
            printErr("\tНе найден счет для записи, идите к админу\n");
            e.printStackTrace();
            printLine();
            return;
        }

        printLine();
        print("\tСумма списана, добавлена транзакция.\n");
    }

    private void makeTransactionBetweenAccounts()
            throws IOException, NumberFormatException, NotAuthorizedException, InvalidSummException {
        printLine();

        //

        List<AccountDto> list = serviceUsers.getAllAccounts();
        printListUserAccounts(list);
        print("\tВыберите с какого счета нужно перевести средства:\n");
        print("\t>>>>>  ");
        int respFrom = readNumberFromConsole();
        if (respFrom >list.size() || respFrom < 0) {
            throw new NumberFormatException();
        }

        //
        print("\tВыберите счет на который следует перевести средства:\n");
        print("\t>>>>>  ");
        int respTo = readNumberFromConsole();
        if (respFrom >list.size() || respTo < 0) {
            throw new NumberFormatException();
        }
        print("\tВведите сумму:\n");
        print("\t>>>>>  ");
        BigDecimal sum = readSumFromConsole();
        serviceUsers.addNewTransactionBetweenUserAccounts(list.get(respFrom-1).getId(), list.get(respTo-1).getId(), sum);

    }

    private void getTransactionSettings() {
        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t> (0) ...назад \n");
        print("\t\t> (1) Удалить транзакцию \n");
        //todo: реализовать отмену транзакции между счетами
        print("\t\t> (2) Отменить транзакцию \n");
        print("\t\t> (3) Добавить новый тип транзакции \n");
        print("\t\t> (4) Показать все транзакции \n");
        print("\t\t> (5) Показать транзакции за определённую дату \n");

        print("\t>>>>> ");

        try {
            int answer = readNumberFromConsole();
            List<TransactionDto> list = serviceUsers.getAllTransactions();
            switch (answer) {
                case 0: {
                    showUserOptions(this.serviceUsers);
                }

                case 1: {
                    printLine();
                    printListTransactions(list);
                    print("\tКакую транзакцию хотите удалить?\n");
                    print("\t>>>>>  ");

                    int resp = readNumberFromConsole();
                    if (resp>list.size() || resp <0) {
                        throw new NumberFormatException();
                    }

                    serviceUsers.deleteTransaction(list.get(resp-1).getId());
                    getTransactionSettings();
                    break;
                }

                case 2: {
                    printLine();
                    printListTransactions(list);
                    print("\tКакую транзакцию хотите отменить?\n");
                    print("\t>>>>>  ");

                    int resp = readNumberFromConsole();
                    if (resp>list.size() || resp <0) {
                        throw new NumberFormatException();
                    }

                    serviceUsers.cancelTransaction(list.get(resp-1).getId());
                    printLine();
                    print("\tТранзакция отменена, средства вернулись на счет");
                    getTransactionSettings();
                    break;
                }

                case 3: {
                    printLine();
                    print("\tВведите новое название категории транзакции\n");
                    print("\t>>>>>  ");
                    String newCategoryName = readStringFromConsole();
                    serviceUsers.addNewCategoryTransaction(newCategoryName);
                    print("\tНовое имя для категории транзакции добавлено\n");
                    getTransactionSettings();
                    break;
                }

                case 4: {
                    printLine();
                    printListTransactions(list);
                    getTransactionSettings();

                }

                case 5: {
                    printLine();
                    print("\tВведите дату в формате dd.mm.yyyy\n");
                    print("\t>>>>>  ");
                    String resp = readStringFromConsole();
                    List<TransactionDto> listTransactionsToDate  = serviceUsers.getAllTransactionsForDate(resp);
                    printListTransactions(listTransactionsToDate);
                    getTransactionSettings();
                }

                default:
                    throw new NumberFormatException();
            }

        } catch (ParseException e) {
            printErr("неправильный формат даты");
        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод");
            getSettingUser();
        } catch (RecordNotFoundException e) {
            e.printStackTrace();
            printErr("Запись не найдена ошибка сервера...");
            getSettingUser();
        }
    }

    private void getSettingUser() {

        printLine();
        print("\tВыберите доступные действия: \n");
        print("\t\t> (0) ...назад \n");
        print("\t\t> (1) Изменить пароль \n");
        print("\t\t> (2) Изменить емаил \n");
        print("\t\t> (3) Удалить пользователя \n");
        print("\t>>>>> ");

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
                    print("\tВы уверены что хотите удалить пользователя и связанные с ним данные?\n");
                    print("\t(1) - удалить пользователя");
                    print("\t>>>>>  ");
                    int resp = readNumberFromConsole();
                    if (resp == 1) {
                        serviceUsers.deleteUser();
                    }
                    printLine();
                    print("\tПользователь удалён");
                    new StartViewConsole().getWelcome();
                    break;
                }

                default:
                    throw new NumberFormatException();
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
        print("\t\t> (0) ...назад \n");
        print("\t\t> (1) Добавить новый счет \n");
        print("\t\t> (2) Добавить новый тип счёта \n");
        print("\t\t> (3) Удалить счет \n");
        print("\t\t> (4) Удалить тип счета\n");
        print("\t\t> (5) Показать Ваши счета\n");
        print("\t>>>>> ");

        try {
            int answer = readNumberFromConsole();
            switch (answer) {
                case 0: {
                    showUserOptions(this.serviceUsers);
                }

                case 1: {
                    printLine();
                    print("\tВыберите из списка тип счета который хотите добавить:\n");
                    List<AccountNameDto> list = serviceUsers.getAllAccountNames();
                    printListNameAccounts(list);
                    print("\t\t>>>>>  ");
                    int resp = readNumberFromConsole();

                    if (resp > list.size() || resp < 1) {
                        throw new NumberFormatException("\tНеверный тип счета");
                    }

                    print("\tВведите стартовую сумму:\n");
                    print("\t\t>>>>>  ");
                    int sum = readNumberFromConsole();
                    serviceUsers.addNewAccount(list.get(resp - 1).getId(), new BigDecimal(sum));
                    printLine();
                    print("\tНовый счёт успешно добавлен");
                    getAccountOperations();
                    break;
                }

                case 2: {
                    printLine();
                    print("\tВведите новое наименование счёта:\n");
                    print("\t>>>>>  ");
                    String resp = readStringFromConsole();
                    serviceUsers.addNewAccountName(resp);
                    printLine();
                    print("\tНовый тип счета создан\n");
                    getAccountOperations();
                }

                case 3: {
                    printLine();
                    print("\tКакой счет хотите удалить?\n");

                    List<AccountDto> list;
                    try {
                        list = serviceUsers.getAllAccounts();
                    } catch (QueryException e) {
                        printLine();
                        printErr("\tУ вас нету счетов\n");
                        getAccountOperations();
                        break;
                    }
                    printListUserAccounts(list);
                    print("\t>>>>>  ");
                    int resp = readNumberFromConsole();

                    if (resp > list.size() || resp < 0) {
                        throw new NumberFormatException();
                    }
                    serviceUsers.deleteAccount(list.get(resp-1).getId());
                    printLine();
                    print("\tСчёт удалён");
                    getAccountOperations();
                }

                case 4: {
                    printLine();
                    print("\tКакой тип наименования желаете удалить?\n");
                    List<AccountNameDto> list = serviceUsers.getAllAccountNames();
                    printListNameAccounts(list);
                    print("\t>>>>>  ");
                    int resp = readNumberFromConsole();

                    if ( resp > list.size() || resp < 0 ) {
                        throw new NumberFormatException();
                    }

                    serviceUsers.deleteAccountName(list.get(resp-1).getId());
                    printLine();
                    print("\tНаименование удалено\n");
                    getAccountOperations();


                }

                case 5: {

                    try {
                         printListUserAccounts(serviceUsers.getAllAccounts());
                    } catch (QueryException e) {
                        printLine();
                        printErr("\tУ вас нету счетов\n");
                        getAccountOperations();
                        break;
                    }
                    getAccountOperations();
                }
                break;
            }

        } catch (IOException | NumberFormatException e) {
            printErr("\tНекорректный ввод\n");
            showUserOptions(this.serviceUsers);
        } catch (NotAuthorizedException e) {
            printErr("\tОшибка сервера, программа закроется\n");
            System.exit(1);
        } catch (AlreadyExistsException e) {
            printErr("\tНаименование уже существует, придумайте другое\n");
            showUserOptions(this.serviceUsers);
        } catch (RecordNotFoundException e) {
            printErr("\tСчёт не найден, ошибка сервера\n");
            showUserOptions(this.serviceUsers);
        }


    }
}
