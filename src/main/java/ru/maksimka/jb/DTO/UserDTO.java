package ru.maksimka.jb.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @With private Integer id;
    @With private String name;
    @With private String password;
    @With private String email;


    @Override
    public String toString() {
        return "User {\n \tid = '" + id + "' \n\t" +
                "name = '" + name + "' \n\t" +
                "password = '" + password + "'  }";
    }
}
