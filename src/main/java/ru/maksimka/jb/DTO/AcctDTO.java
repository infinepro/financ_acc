package ru.maksimka.jb.DTO;

import ru.maksimka.jb.containers.User;

public class AcctDTO {
    private int id;
    private String nameAcct;
    private int balance;

    public AcctDTO() {
    }

    public AcctDTO(String nameAcct, int balance) {
        this.nameAcct = nameAcct;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public AcctDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getNameAcct() {
        return nameAcct;
    }

    public AcctDTO setNameAcct(String nameAcct) {
        this.nameAcct = nameAcct;
        return this;
    }

    public int getBalance() {
        return balance;
    }

    public AcctDTO setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public String toString() {
        return "\t" + nameAcct + "\t" + balance;
    }
}
