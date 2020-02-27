package ru.maksimka.jb.domain.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PasswordEncoder {

    public String encript(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }
}
