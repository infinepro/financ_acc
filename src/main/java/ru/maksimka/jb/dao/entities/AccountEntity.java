package ru.maksimka.jb.dao.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Accessors(chain = true)
@EqualsAndHashCode

//hibernate
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_name_id")
    private AccountNamesEntity accountName;

    @Column(name = "balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "account")
    private List<TransactionEntity> transactionsList;

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", accountName=" + accountName +
                ", balance=" + balance +
                ", owner=" + owner +
                ", transactionsList=" + transactionsList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountEntity)) return false;
        AccountEntity that = (AccountEntity) o;
        return getId().equals(that.getId());
    }
}
