package ru.maksimka.jb.services;

import ru.maksimka.jb.DAO.AcctDAO;
import ru.maksimka.jb.DAO.DAOFactory;
import ru.maksimka.jb.DAO.TransactionDAO;
import ru.maksimka.jb.containers.Acct;
import ru.maksimka.jb.containers.Transaction;
import ru.maksimka.jb.exceptions.TransactionFailException;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateTransactionService {

    private final AcctDAO acctDao;
    private final TransactionDAO transactionDAO;

    public CreateTransactionService() {
        acctDao = new AcctDAO();
        this.transactionDAO = new TransactionDAO();
    }

    public CreateTransactionService(AcctDAO acctDao) {
        this.acctDao = acctDao;
        this.transactionDAO = new TransactionDAO();
    }

    public void createTransaction(Integer fromAcctId, Integer toAcctId, Integer sum) throws TransactionFailException {

        Connection connection = null;

        try {
            connection = DAOFactory.getConnection();
            connection.setAutoCommit(false);

            Acct acctFrom = acctDao.findBy(fromAcctId);
            if (acctFrom == null) {
                throw new TransactionFailException("Не найден счет для списания " + fromAcctId);
            }

            if (sum > acctFrom.getBalance()) {
                throw new TransactionFailException("Нехватает средств на балансе аккаунта" + fromAcctId);
            }

            Acct acctTo = acctDao.findBy(toAcctId);
            if (acctTo == null) {
                throw new TransactionFailException("Не найден счет для пополнения " + toAcctId);
            }

            Transaction transactionTo =  new Transaction();
            transactionTo.setCategory(0);
            transactionTo.setSum(+sum);
            transactionTo.setUniq_account_id(toAcctId);

            Transaction transactionFrom =  new Transaction();
            transactionFrom.setCategory(0);
            transactionFrom.setSum(-sum);
            transactionFrom.setUniq_account_id(fromAcctId);

            acctFrom.setBalance(acctFrom.getBalance() - sum);
            acctTo.setBalance(acctTo.getBalance() + sum);

            acctDao.update(acctFrom, connection);
            acctDao.update(acctTo, connection);
            transactionDAO.insert(transactionFrom, connection);
            transactionDAO.insert(transactionTo, connection);

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
