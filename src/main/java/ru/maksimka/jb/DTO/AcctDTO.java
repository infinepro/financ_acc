package ru.maksimka.jb.DTO;

import ru.maksimka.jb.containers.User;

public class AcctDTO {
    private int id;
    private String nameAcct;
    private int balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AcctDTO(String nameAcct, int balance) {
        this.nameAcct = nameAcct;
        this.balance = balance;
    }

    public String getNameAcct() {
        return nameAcct;
    }

    public void setNameAcct(String nameAcct) {
        this.nameAcct = nameAcct;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "\t" + nameAcct + "\t" + balance;
    }
}
