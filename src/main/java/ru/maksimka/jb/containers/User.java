package ru.maksimka.jb.containers;

import java.util.List;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private List<Acct> acctList;

    public List<Acct> getAcctList() {
        return acctList;
    }

    public void setAcctList(List<Acct> acctList) {
        this.acctList = acctList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User {\n \tid = '" + id + "' \n\t" +
                "name = '" + name + "' \n\t" +
                "password = '" + password + "'  }";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
