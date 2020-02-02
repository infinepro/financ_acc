package ru.maksimka.jb.console_views;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.TransactionDto;

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

    //NOT IMPL
    protected void printListUserAccounts(List<AccountDto> list) {

    }

    protected void printListNameAccounts() {

    }

    protected void printListTransactions(List<TransactionDto> list) {

    }

    protected int readNumberFromConsole() throws NumberFormatException, IOException {
        return Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }

    protected String readStringFromConsole() throws IOException {
        return (new BufferedReader(new InputStreamReader(System.in))).readLine();
    }

    protected BigDecimal readSumFromConsole() throws NumberFormatException, IOException {
        return new BigDecimal(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }
}
