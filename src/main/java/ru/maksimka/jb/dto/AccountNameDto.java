package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.stereotype.Component;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

@Component
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
