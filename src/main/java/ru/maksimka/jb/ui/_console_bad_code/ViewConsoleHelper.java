package ru.maksimka.jb.ui._console_bad_code;

import ru.maksimka.jb.domain.dto.AccountDto;
import ru.maksimka.jb.domain.dto.AccountNameDto;
import ru.maksimka.jb.domain.dto.TransactionCategoryDto;
import ru.maksimka.jb.domain.dto.TransactionDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                    "%-4s%-30s%n", ("\t\t(" + count++ + ")"), dto.getAccountName());
        }
    }

    protected void printListTransactions(List<TransactionDto> list) {
        int count = 1;
        System.out.printf(
                "%-4s%-30s%-30s%-15s%-15s%n", ("\t\t  "), "Категория", "Наименование счета", "Сумма", "дата");

        for (TransactionDto dto : list) {
            System.out.printf(
                    "%-4s%-30s%-30s%-15s%-15s%n",
                    "\t\t(" + count++ + ") ",
                    dto.getCategoryName(),
                    dto.getAccountName(),
                    dto.getSum(),
                    dto.getDate());
        }
    }

    protected void printListTransactionCategories(List<TransactionCategoryDto> list) {
        int count = 1;
        for (TransactionCategoryDto dto : list) {
            System.out.printf(
                    "%-4s%-30s%n", ("\t\t(" + count++ + ")"), dto.getCategoryName());
        }
    }

    protected int readNumberFromConsole() throws NumberFormatException, IOException {
        //return new Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        int i = Integer.parseInt(r.readLine());
        return i;
    }

    protected String readStringFromConsole() throws IOException {
        //return new BufferedReader(new InputStreamReader(System.in)).readLine());
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String str = r.readLine();
        return str;
    }

    protected BigDecimal readSumFromConsole() throws NumberFormatException, IOException {
        //return new BigDecimal( new BufferedReader(new InputStreamReader(System.in)).readLine());
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        BigDecimal bd = new BigDecimal(r.readLine());
        return bd;
    }


}
