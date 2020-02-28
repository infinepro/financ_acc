package ru.maksimka.jb.domain.dto.roles;

import org.springframework.security.core.GrantedAuthority;

public class Roles implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "USER";
    }
}
