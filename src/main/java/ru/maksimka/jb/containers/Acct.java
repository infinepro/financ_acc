package ru.maksimka.jb.containers;

public class Acct {
    private Integer id;
    private String userName;
    private Integer typeAcct;
    private Integer balance;

    public Acct(){}

    public Acct(String userName, Integer balance, Integer typeAcct) {
        this.userName = userName;
        this.typeAcct = typeAcct;
        this.balance = balance;
    }

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
