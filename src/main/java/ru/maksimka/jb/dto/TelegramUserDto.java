package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.stereotype.Component;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

@Component
public class TelegramUserDto {



    private int id;
    private String nameUser;
    private long chatId;
    private String phoneNumber;
    private String email;
}
