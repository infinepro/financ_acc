package ru.maksimka.jb.dao.entities;

import lombok.*;

import javax.persistence.*;

//lombok
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@EqualsAndHashCode

@Entity
@Table(name = "telegram_user")
public class TelegramUserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
