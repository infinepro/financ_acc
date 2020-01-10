package ru.maksimka.jborn.view;

import ru.maksimka.jborn.service.UserVerification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserViewQuestions {

    private UserVerification userVerification;
    boolean ifSignIn = false;
    private String login;
    private String password;

    UserViewQuestions() {
        userVerification = new UserVerification();
    }

    public void ifRegistered() throws IOException {

        if (ifSignIn == false) {

            System.out.print("Введите логин: ");
            this.login = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println("\nВведите пароль: ");
            this.password = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }

        if (userVerification.verificationUser(login, password)) {
            ifSignIn = true;
            UserOperations operations = new UserOperations(login);
            System.out.println("Доступ к вашим операциям открыт\n Вы можете использовть одну из четырёх операций: \n" +
                    "\t1 - Отобразить ваши счета\n" +
                    "\t2 - Создать новый счет\n" +
                    "\t3 - Добавить новую категорию транзакции(расходов или доходов)\n" +
                    "\t4 - Добавить новое наименование счета");
            String numberOperation = new BufferedReader(new InputStreamReader(System.in)).readLine();
            switch (Integer.parseInt(numberOperation)) {
                case 1:
                    operations.getUserAccounts();
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
            System.out.println("\nВведите свой email: ");
            String email = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println("Введите пароль: ");
            String password = new BufferedReader(new InputStreamReader(System.in)).readLine();

            if (userVerification.findUserByName(login)) {
                if (userVerification.registerUser(login, email, password)) {
                    System.out.println("Вы успешно зарегистрировались");
                    break;
                } else {
                    System.out.println("Произошла ошибка, попробуйте ещё раз");
                }
            } else {
                System.out.println("Пользователь с таким логином существует, придумайте другой логин или авторизуйтесь");
            }
        }
    }
}
