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
