package ru.maksimka.jb.DAO.Domain;

public class Account {
    private Integer id;
    private String userName;
    private Integer idCategoryAccount;
    private Integer balance;

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    private String typeAccount;

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

    public Integer getIdCategoryAccount() {
        return this.idCategoryAccount;
    }

    public void setIdCategoryAccount(Integer accName ) {
        this.idCategoryAccount = accName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;

}
