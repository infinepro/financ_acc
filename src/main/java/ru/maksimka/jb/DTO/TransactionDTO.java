package ru.maksimka.jb.DTO;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    @With private Integer id;
    @With private Integer uniq_account_id;
    @With private BigDecimal sum;
    @With private String date;
    @With private Integer category;

}
