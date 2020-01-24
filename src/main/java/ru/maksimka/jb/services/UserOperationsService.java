package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DAO.*;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionDTO;
import ru.maksimka.jb.DTO.UserDTO;
import ru.maksimka.jb.exceptions.UserNotFoundException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOperationsService {


    private String name;
    private UserDAO userDAO;
    private AcctDAO acctDAO;
    private TypeAcctDAO typeAcctDAO;
    private TransactionDAO transactionDAO;

    public UserOperationsService(UserDAO userDAO, AcctDAO acctDAO, TypeAcctDAO typeAcctDAO, TransactionDAO transactionDAO) {
        this.userDAO = userDAO;
        this.acctDAO = acctDAO;
        this.typeAcctDAO = typeAcctDAO;
        this.transactionDAO = transactionDAO;
    }

    public void setLogin(String login) {

        UserDTO userDTO = null;
        try {
            userDTO = userDAO.findBy(login);
        } catch (SQLException | UserNotFoundException e) {
            e.printStackTrace();
        }
        if (userDTO != null) {
            this.name = userDTO.getName();
        } else this.name = "";
    }

    public boolean addNewAcct(String name, BigDecimal balance, Integer idCategory) {
        AcctDTO acct = new AcctDTO()
                .withTypeAcctId(idCategory)
                .withUserName(name)
                .withBalance(balance);
        return acctDAO.insert(acct);
    }

    public boolean addNewTypeAcct(String type) {
        return typeAcctDAO.insert(type);
    }

    public boolean addNewTypeTransaction(String type) {
        return transactionDAO.addNewTypeTransaction(type);
    }

    public boolean addNewTransaction() {
        return false;
    }

    public List<TransactionDTO> getAllTransactions() {
        return null;
    }

    public List<AcctDTO> getAllAcct() {
        List<AcctDTO> listAcct;
        try {
            listAcct = acctDAO.findByAll(name);
            return listAcct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getAllTypeAccts(DataSource dataSource) {

        List<String> listTypeAccts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT category_account FROM names_accounts");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listTypeAccts.add(rs.getString("category_account"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTypeAccts;
    }


}
