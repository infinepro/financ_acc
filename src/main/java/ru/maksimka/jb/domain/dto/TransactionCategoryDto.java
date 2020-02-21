package ru.maksimka.jb.domain.dto;


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
public class TransactionCategoryDto {
    private Integer id;
    private String categoryName;

    @Override
    public String toString() {
        return "TransactionCategoryDto{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
