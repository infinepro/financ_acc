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
    private Integer accountId;
    private Integer transactionCategoryId;
    private String accountName;
    private String categoryName;
    private BigDecimal sum;
    private Date date;

    @Override
    public String toString() {
        return "TransactionDto{" +
                "\t\nid=" + id +
                ",\t\naccountId=" + accountId +
                ",\t\ntransactionCategoryId=" + transactionCategoryId +
                ",\t\naccountName='" + accountName + '\'' +
                ",\t\ncategoryName='" + categoryName + '\'' +
                ",\t\nsum=" + sum +
                ",\t\ndate=" + date +
                '}';
    }
}
