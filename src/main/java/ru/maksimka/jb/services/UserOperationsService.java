package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DAO.*;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionsDTO;
import ru.maksimka.jb.containers.Acct;
import ru.maksimka.jb.containers.User;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOperationsService implements UserOperations<String, Integer> {

    private String name;

    public UserOperationsService() {

    }

    public void setLogin (String login) {
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findBy(login);
        } catch (SQLException  | UserNotFoundException e) {
            e.printStackTrace();
        }
        if (user != null){
            this.name = user.getName();
        } else this.name = "";
    }

    @Override
    public boolean addNewAcct(String name, Integer balance, Integer idCategory) {
        AcctDAO acctDAO = new AcctDAO();
        Acct acct = new Acct(name, balance, idCategory);
        return acctDAO.insert(acct);
    }

    @Override
    public boolean addNewTypeAcct(String type) {
        TypeAcctDAO typeAcctDAO = new TypeAcctDAO();
        return typeAcctDAO.insert(type);
    }

    @Override
    public boolean addNewTypeTransaction(String type) {
        TransactionDAO transactionDAO = new TransactionDAO();
        return transactionDAO.addNewTypeTransaction(type);
    }

    @Override
    public boolean addNewTransaction() {
        return false;
    }

    @Override
    public List<TransactionsDTO> getAllTransactions() {
        return null;
    }

    @Override
    public List<AcctDTO> getAllAcct() {
        List<AcctDTO> listAcct;
        AcctDAO acctDAO = new AcctDAO();
        try {
            listAcct = acctDAO.findByAll(name);
            return listAcct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getAllTypeAccts(DataSource dataSource){

        List<String> listTypeAccts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT category_account FROM names_accounts");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                listTypeAccts.add(rs.getString("category_account"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTypeAccts;
    }


}
