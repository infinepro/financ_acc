package ru.maksimka.jb.web.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    @With
    private boolean success;
    @With
    private Integer userId;
}
