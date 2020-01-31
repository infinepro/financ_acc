package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class AccountDto {
    private Integer id;
    private String owner;
    private String nameAccount;
    private BigDecimal balance;

    @Override
    public String toString() {
        return "AccountDto{" +
                "owner='" + owner + '\'' +
                ", nameAccount='" + nameAccount + '\'' +
                ", balance=" + balance +
                '}';
    }
}
