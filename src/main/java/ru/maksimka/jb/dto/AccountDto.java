package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With


//spring
@Component
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
