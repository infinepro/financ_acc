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

    public Acct setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Acct setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Integer getTypeAcct() {
        return typeAcct;
    }

    public Acct setTypeAcct(Integer typeAcct) {
        this.typeAcct = typeAcct;
        return this;
    }

    public Integer getBalance() {
        return balance;
    }

    public Acct setBalance(Integer balance) {
        this.balance = balance;
        return this;
    }
}
