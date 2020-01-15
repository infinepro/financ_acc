package ru.maksimka.jb.view;

import ru.maksimka.jb.DAO.AcctDAO;
import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.containers.Acct;
import ru.maksimka.jb.services.UserAuth;
import ru.maksimka.jb.services.UserAuthImpl;
import ru.maksimka.jb.services.UserOperations;
import ru.maksimka.jb.services.UserOperationsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ViewService {

    private boolean ifSignIn = false;
    private String login;
    private String password;

    public void ifRegistered() throws IOException {

        UserAuth userAuth = new UserAuthImpl();

        if (ifSignIn == false) {

            System.out.print("Введите логин: ");
            this.login = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.print("\nВведите пароль: ");
            this.password = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }

        if (userAuth.authUser(login, password)) {
            ifSignIn = true;
            UserOperations operations = new UserOperationsImpl(login);
            System.out.println("Доступ к вашим операциям открыт\n Вы можете использовть одну из четырёх операций: \n" +
                    "\t1 - Отобразить ваши счета\n" +
                    "\t2 - Создать новый счет\n" +
                    "\t3 - Добавить новую категорию транзакции(расходов или доходов)\n" +
                    "\t4 - Добавить новое наименование счета");
            String numberOperation = new BufferedReader(new InputStreamReader(System.in)).readLine();
            switch (Integer.parseInt(numberOperation)) {

                case 1: {
                    List<AcctDTO> accts = operations.getAllAcct();
                    for (AcctDTO acctDTO : accts) {
                        System.out.println(acctDTO + " руб.");
                    }
                    break;
                }

                case 2: {
                    List<String> listTypeAccts = (ArrayList<String>) operations.getAllTypeAccts();

                    for (int i = 0; i < listTypeAccts.size(); i++) {
                        System.out.println("\t" + " (" + i + ") " + listTypeAccts.get(0));
                    }

                    System.out.print("\nВыберите из списка какой счёт хотите добавить: ");
                    Integer numberAcct = Integer.parseInt(
                            new BufferedReader(new InputStreamReader(System.in)).readLine());
                    System.out.print("\nНапишите текущий баланс на счёте: ");
                    Integer balance = Integer.parseInt(
                            new BufferedReader(new InputStreamReader(System.in)).readLine());

                    if (operations.addNewAcct(login, balance, numberAcct)) {
                        System.out.println("Счёт успешно создан");
                    } else System.out.println("Счёт не добавлен, свяжитесь с админом!");
                    break;
                }

                case 3: {
                    System.out.println("Введите имя нового типа транзакции: ");
                    String nameType = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    if (operations.addNewTypeTransaction(nameType)) {
                        System.out.println("Новый тип транзакции создан");
                    } else System.out.println("Новый тип транзакции не добавлен, свяжитесь с админом!");
                    break;
                }

                case 4: {
                    System.out.println("Введите имя нового типа счета: ");
                    String nameType = new BufferedReader(new InputStreamReader(System.in)).readLine();
                    if (operations.addNewTypeAcct(nameType)) {
                        System.out.println("Новый тип счета создан");
                    } else System.out.println("Новый тип счета не добавлен, свяжитесь с админом!");
                    break;
                }
                default:
                    System.out.println("Ошибка ввода");
            }

            ifRegistered();
        }
    }

    public void ifNotRegistered() throws IOException {
        while (true) {
            System.out.print("Введите логин: ");
            String login = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.print("\nВведите email: ");
            String email = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.print("\nВведите пароль: ");
            String password = new BufferedReader(new InputStreamReader(System.in)).readLine();
            UserAuth userAuth = new UserAuthImpl();

            if (userAuth.registerUser(login, password, email)) {
                System.out.println("Вы успешно зарегистрировались");
                ifRegistered();
            } else {
                System.out.println("Произошла ошибка, попробуйте ещё раз");
                ifNotRegistered();
            }
        }
    }
}
