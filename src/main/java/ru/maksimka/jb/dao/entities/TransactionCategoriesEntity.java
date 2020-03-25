package ru.maksimka.jb.dao.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@EqualsAndHashCode

//hibernate
@Entity
@Table(name = "transaction_categories")
public class TransactionCategoriesEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "category_name")
    private String nameCategory;

    @OneToMany(mappedBy = "transactionCategory", orphanRemoval = true)
    private List<TransactionEntity> transactionsList;
}
