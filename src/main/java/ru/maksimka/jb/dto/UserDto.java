package ru.maksimka.jb.dto;

import lombok.*;
import org.springframework.stereotype.Component;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

//spring
@Component
public class UserDto {

    private Integer id;
    private String name;
    private String password;
    private String email;

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
