package ru.maksimka.jb.containers;

public class Transaction {
    private Integer id;
    private Integer uniq_account_id;
    private Integer sum;
    private String date;
    private Integer category;

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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
