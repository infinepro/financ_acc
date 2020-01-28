package ru.maksimka.jb.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With

//hibernate
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountEntity> accountsList;

    @Override
    public String toString() {
        return "User {\n \tid = '" + id + "' \n\t" +
                "name = '" + name + "' \n\t" +
                "password = '" + password + "'  }";
    }
}
