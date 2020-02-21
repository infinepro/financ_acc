package ru.maksimka.jb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

@Component
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
