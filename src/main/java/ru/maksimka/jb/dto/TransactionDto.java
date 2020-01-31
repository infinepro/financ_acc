package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.sql.Date;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class TransactionDto {
    private Integer id;
    private String accountName;
    private String categoryName;
    private BigDecimal sum;
    private Date date;

    @Override
    public String toString() {
        return "TransactionDto{" +
                "accountName='" + accountName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", sum=" + sum +
                ", date=" + date +
                '}';
    }
}