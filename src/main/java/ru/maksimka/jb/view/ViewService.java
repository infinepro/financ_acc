package ru.maksimka.jb.view;

import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.services.UserAuth;
import ru.maksimka.jb.services.UserAuthImpl;
import ru.maksimka.jb.services.UserOperations;
import ru.maksimka.jb.services.UserOperationsImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ViewService {

    boolean ifSignIn = false;
    private String login;
    private String password;

    public void ifRegistered() throws IOException {

        UserAuth userAuth = new UserAuthImpl();

        if (ifSignIn == false) {

            System.out.print("Введите логин: ");
            this.login = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println("\nВведите пароль: ");
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
                    for(AcctDTO acctDTO : accts) {
                        System.out.println(acctDTO);
                    }
                }
                    break;
                case 2:
                    operations.createNewAccount();
                    break;
                case 3:
                    operations.setNewCategoryTransaction();
                    break;
                case 4:
                    operations.createNewAccountType();
                    break;
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
            System.out.print("\nВведите свой email: ");
            String email = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println("\nВведите пароль: ");
            String password = new BufferedReader(new InputStreamReader(System.in)).readLine();
            UserAuth userAuth = new UserAuthImpl();

            if (userAuth.registerUser(login, email, password)) {
                System.out.println("Вы успешно зарегистрировались");
                ifRegistered();
            } else {
                System.out.println("Произошла ошибка, попробуйте ещё раз");
                ifNotRegistered();
            }
        }
    }
}
