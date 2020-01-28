package ru.maksimka.jb.entities;

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

    @Column(name = "nameCategory")
    private String nameCategory;

    @OneToMany(mappedBy = "transactionCategory")
    private List<TransactionEntity> transactionsList;
}
