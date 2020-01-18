package ru.maksimka.jb.containers;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
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

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
