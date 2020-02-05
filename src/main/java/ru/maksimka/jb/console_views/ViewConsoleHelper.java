package ru.maksimka.jb.console_views;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.AccountNameDto;
import ru.maksimka.jb.dto.TransactionCategoryDto;
import ru.maksimka.jb.dto.TransactionDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.List;

public abstract class ViewConsoleHelper {

    protected void printLine() {
        System.out.println("\n################################################" +
                "#####################################");
    }

    protected void print(String s) {
        System.out.print(s);
    }

    protected void printErr(String s) {
        System.err.print(s);
    }

    protected void printListUserAccounts(List<AccountDto> list) {
        int count = 1;
        for (AccountDto dto : list) {
            System.out.printf(
                    "%-4s%-30s%10s%5s%n", ("\t\t(" + count++ + ")"), dto.getNameAccount(), dto.getBalance(), "руб.");
        }
    }

    protected void printListNameAccounts(List<AccountNameDto> list) {
        int count = 1;
        for (AccountNameDto dto : list) {
            System.out.printf(
                    "%-4s%-30s%n", ( "\t\t(" + count++ + ")"), dto.getAccountName());
        }
    }

    protected void printListTransactions(List<TransactionDto> list) {

    }

    protected void printListTransactionCategories(List<TransactionCategoryDto> list) {
        int count = 1;
        for (TransactionCategoryDto dto : list) {
            System.out.printf(
                    "%-4s%-30s%n", ( "\t\t(" + count++ + ")"), dto.getCategoryName());
        }
    }

    protected int readNumberFromConsole() throws NumberFormatException, IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        int i = Integer.parseInt(r.readLine());
        r.close();
        return i;
    }

    protected String readStringFromConsole() throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String str = r.readLine();
        r.close();
        return str;
    }

    protected BigDecimal readSumFromConsole() throws NumberFormatException, IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        BigDecimal bd = new BigDecimal( r.readLine());
        r.close();
        return bd;
    }


}
