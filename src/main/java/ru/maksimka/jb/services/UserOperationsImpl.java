package ru.maksimka.jb.services;

import ru.maksimka.jb.DAO.AcctDAO;
import ru.maksimka.jb.DAO.UserDAO;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionsDTO;
import ru.maksimka.jb.containers.Acct;
import ru.maksimka.jb.containers.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserOperationsImpl implements UserOperations {

    private String name;

    public UserOperationsImpl (String login) {
        UserDAO userDAO = new UserDAO();
        User user = null;
        try {
            user = userDAO.findBy(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null){
            this.name = user.getName();
        } else this.name = "";
    }

    @Override
    public boolean addNewAcct() {
        return false;
    }

    @Override
    public boolean addNewTypeAcct() {
        return false;
    }

    @Override
    public boolean addNewTypeTransaction() {
        return false;
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

        List<Acct> listAcct = new ArrayList<>();
        AcctDAO acctDAO = new AcctDAO();

        acctDAO.




        return null;
    }
}
