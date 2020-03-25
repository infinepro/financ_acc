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
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name" ,unique = true)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountEntity> accountsList;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
