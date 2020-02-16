package ru.maksimka.jb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

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
