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
@Table(name = "account_names")
public class AccountNamesEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_name")
    private String accountName;

    @OneToMany(mappedBy = "accountName", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AccountEntity> listAccounts;


}
