package ru.maksimka.jb.dao.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Accessors(chain = true)
@EqualsAndHashCode

//hibernate
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "date")
    //@Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private TransactionCategoriesEntity transactionCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

}
