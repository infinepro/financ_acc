package ru.maksimka.jborn.DAO.Domain;

public class Transaction {
    private Integer id;
    private Integer uniq_account_id;
    private String sum;
    private String date;
    private String category;

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUniq_account_id() {
        return uniq_account_id;
    }

    public void setUniq_account_id(Integer uniq_account_id) {
        this.uniq_account_id = uniq_account_id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
