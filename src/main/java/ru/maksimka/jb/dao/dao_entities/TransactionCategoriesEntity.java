package ru.maksimka.jb.dao.dao_entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.util.List;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With

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
