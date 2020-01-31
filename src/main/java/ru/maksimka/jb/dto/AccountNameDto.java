package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class AccountNameDto {
    private Integer id;
    private String accountName;

    @Override
    public String toString() {
        return "AccountNameDto{" +
                "accountName='" + accountName + '\'' +
                '}';
    }
}
