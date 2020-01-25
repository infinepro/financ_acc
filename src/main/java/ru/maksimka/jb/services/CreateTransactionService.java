package ru.maksimka.jb.services;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.DAO.AcctDAO;
import ru.maksimka.jb.DAO.TransactionDAO;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.DTO.TransactionDTO;
import ru.maksimka.jb.exceptions.TransactionFailException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;


@Service
public class CreateTransactionService {

    private final AcctDAO acctDao;
    private final TransactionDAO transactionDAO;
    private final DataSource dataSource;

    public CreateTransactionService(AcctDAO acctDAO, TransactionDAO transactionDAO, DataSource dataSource) {
        this.acctDao = acctDAO;
        this.transactionDAO = transactionDAO;
        this.dataSource = dataSource;
    }

    public void createTransaction(Integer fromAcctId, Integer toAcctId, BigDecimal sum) throws TransactionFailException {

        Connection connection = null;

        try {

            AcctDTO acctFrom = acctDao.findBy(fromAcctId);
            if (acctFrom == null) {
                throw new TransactionFailException("Не найден счет для списания " + fromAcctId);
            }

            if (sum.compareTo(acctFrom.getBalance()) == 1L) {
                throw new TransactionFailException("Нехватает средств на балансе аккаунта" + fromAcctId);
            }

            AcctDTO acctTo = acctDao.findBy(toAcctId);
            if (acctTo == null) {
                throw new TransactionFailException("Не найден счет для пополнения " + toAcctId);
            }

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            TransactionDTO transactionDTOTo = new TransactionDTO()
                    .withCategory(1)
                    .withSum(sum)
                    .withUniq_account_id(toAcctId);

            TransactionDTO transactionDTOFrom = new TransactionDTO()
                    .withCategory(1)
                    .withSum(sum.negate())
                    .withUniq_account_id(toAcctId);

            acctFrom.setBalance(acctFrom.getBalance().subtract(sum));
            acctTo.setBalance(acctTo.getBalance().add(sum));

            acctDao.update(acctFrom, connection);
            acctDao.update(acctTo, connection);
            transactionDAO.insert(transactionDTOFrom, connection);
            transactionDAO.insert(transactionDTOTo, connection);

            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
