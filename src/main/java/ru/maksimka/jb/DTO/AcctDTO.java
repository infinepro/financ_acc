package ru.maksimka.jb.DTO;

import lombok.*;


import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcctDTO {
    @With private int id;
    @With private String userName;
    @With private Integer typeAcctId;//id_category_account
    @With private String nameAcct;
    @With private BigDecimal balance;




    @Override
    public String toString() {
        return "\t" + balance;
    }
}
