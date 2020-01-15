package ru.maksimka.jb.containers;

public class Acct {
    private Integer id;

    public Acct(String userName, Integer typeAcct, Integer balance) {
        this.id = id;
        this.userName = userName;
        this.typeAcct = typeAcct;
        this.balance = balance;
    }

    private String userName;
    private Integer typeAcct;
    private Integer balance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTypeAcct() {
        return typeAcct;
    }

    public void setTypeAcct(Integer typeAcct) {
        this.typeAcct = typeAcct;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
