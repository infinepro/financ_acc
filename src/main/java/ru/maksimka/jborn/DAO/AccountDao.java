package ru.maksimka.jborn.DAO;

import ru.maksimka.jborn.DAO.Domain.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<List<Account>, String> {

    @Override
    public List<Account> findBy(String name) {
        ArrayList<Account> accountList = new ArrayList<>();
        Account account = null;

        try (Connection connection = DaoFactory.getConnection()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "SELECT * FROM accounts " +
                                    "JOIN names_accounts na on accounts.id_category_account = na.id WHERE user_name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {

                while (resultSet.next()) {
                    account = new Account();
                    account.setUserName(resultSet.getString("user_name"));
                    account.setIdCategoryAccount(resultSet.getInt("id_category_account"));
                    account.setBalance(resultSet.getInt("balance"));
                    account.setId(resultSet.getInt("id"));
                    account.setDate(resultSet.getString("date"));
                    account.setTypeAccount(resultSet.getString("category_account"));

                    accountList.add(account);
                }
            }
            return accountList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<List<Account>> findByAll() throws Exception {
        return null;
    }


    //пришлось делатьлист чтобы не переписывать все
    @Override
    public boolean insert(List<Account> accountList) {

        Account account = accountList.get(0);

        try (Connection connection = DaoFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO accounts(user_name, id_category_account, balance, date) VALUES (?,?,?, CURRENT_DATE )");
            preparedStatement.setString(1, account.getUserName());
            preparedStatement.setInt(2, account.getIdCategoryAccount());
            preparedStatement.setInt(3, account.getBalance());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при добавлении нового счета");
            return false;
        }


    }

    @Override
    public boolean update(List<Account> account) {
        return false;
    }

    @Override
    public boolean delete(String integer) {
        return false;
    }
}


