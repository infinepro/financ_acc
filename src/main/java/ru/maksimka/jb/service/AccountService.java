package ru.maksimka.jb.service;

import ru.maksimka.jb.DAO.AccountDao;
import ru.maksimka.jb.DAO.Domain.Account;
import ru.maksimka.jb.DAO.TypeAccountDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AccountService {

    private String login;
    private ArrayList<Account> accountList;
    private Account account;

    public AccountService(String login) {
        this.login = login;
    }

    public String getAllUserAccount() {
        AccountDao accountDao = new AccountDao();
        accountList = (ArrayList<Account>) accountDao.findBy(login);
        if (accountList.size() == 0) {
            return "У вас ещё нет счетов";
        }
        StringBuilder listUserAccounts = new StringBuilder();

        Iterator<Account> iterator = accountList.iterator();

        while (iterator.hasNext()) {
            account = iterator.next();
            listUserAccounts.append(account.getTypeAccount() + "\t" + account.getBalance() + "\n");
        }
        return listUserAccounts.toString();
    }

    public Integer checkNumberAccount() {
        AccountDao accountDao = new AccountDao();
        accountList = (ArrayList<Account>) accountDao.findBy(login);
        return accountList.size();
    }

    public String chekAllTypeAccounts() {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        TypeAccountDAO typeAccountDAO = new TypeAccountDAO();
        try {
            list = (ArrayList<String>) typeAccountDAO.findByAll();

            Iterator<String> iterator = list.iterator();
            int count = 1;
            while (iterator.hasNext()) {
                stringBuilder.append("" + count + "\t" + iterator.next() + "\n");
                count++;
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean setNewUserAccount(String login, Integer idAccount, Integer balance) {
        AccountDao accountDao = new AccountDao();

        Account account = new Account();
        account.setUserName(login);
        account.setIdCategoryAccount(idAccount);
        account.setBalance(balance);

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        return accountDao.insert(accountList);
    }

    public String setNewTypeAccount() {
        TypeAccountDAO typeAccountDAO = new TypeAccountDAO();
        try {
            System.out.println("Введите название нового типа счета");
            String type = new BufferedReader(new InputStreamReader(System.in)).readLine();

            if (typeAccountDAO.insert(type)) {
                return "Новый тип счета добавлен успешно!";
            } else {
                throw new Exception();
            }
        } catch (IOException e) {
            return "Произошла ошибка ввода в консоль, новый тип счета не добавлен";
        } catch (Exception e) {
            return "Произошла ошибка при добавлении, возможно такой тип счета уже есть !";
        }
    }
}
