package ru.maksimka.jborn.view;

import ru.maksimka.jborn.service.AccountService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

class UserOperations {

    String login;
    AccountService accountService;

    UserOperations(String login) {
        accountService = new AccountService(login);
        this.login = login;
    }

    public void getUserAccounts() {
        System.out.println(accountService.getAllUserAccount());
    }

    public void createNewAccount() {

        if (accountService.checkNumberAccount() >=5) {
            System.out.println("У вас 5 счетов, нельзя создавать больше");
            return;
        }

        try {
            System.out.println("Выберите тип счёта (1/2/3...): ");
            System.out.println(accountService.chekAllTypeAccounts());



            Integer idAccount = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
            System.out.println("\nВведите баланс данного счета: ");
            Integer balance = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

            if (accountService.setNewUserAccount(login, idAccount, balance)){
                System.out.println("Счёт успешно добавлен");
            } else {throw  new IOException();}

        } catch (IOException e) {
            System.out.println("Произошла ошибка ввода с клавиатуры, или неполадки с базой данных");
        }
        System.out.println();
    }

    public void setNewCategoryTransaction() {

    }

    public void createNewAccountType() {

        System.out.println(accountService.setNewTypeAccount());



    }


}
