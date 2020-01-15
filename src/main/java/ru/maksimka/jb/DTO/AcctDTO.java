package ru.maksimka.jb.DTO;

import ru.maksimka.jb.containers.User;

public class AcctDTO {
    private int id;
    private String nameAcct;
    private int balance;
    // private User owner;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public AcctDTO(int id, int balance) {
        this.id = id;
        this.balance = balance;


    }

    public String getNameAcct() {
        return nameAcct;
    }

    public void setNameAcct(String nameAcct) {
        this.nameAcct = nameAcct;
    }

    @Override
    public String toString() {
        return "\t" + nameAcct + "\t" + balance;
    }
}
