package ru.maksimka.jb.console_views;

import ru.maksimka.jb.dto.AccountDto;
import ru.maksimka.jb.dto.TransactionDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractViewConsole {

    protected void print(String s) {
        System.out.println(s);
    }

    protected void printErr(String s) {
        System.err.println(s);
    }

    //NOT IMPL
    protected void printListUserAccounts(List<AccountDto> list){

    }

    protected void printListTransactions(List<TransactionDto> list) {

    }

    protected String readStringFromConsole() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    protected BigDecimal readNumberFromConsole() throws NumberFormatException, IOException {
        return new BigDecimal(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }
}
