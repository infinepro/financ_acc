package ru.maksimka.jb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@With

@Component
public class UserDto implements UserDetails {


    private Integer id;
    private String name;
    private String password;
    private String email;

    //todo: перенести в базу заглушки если потребуется..пересмотреть
    //for spring security
    private List<GrantedAuthority> grantedAuthorityList;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //todo: заглушка, исправить
        grantedAuthorityList.add((GrantedAuthority) () -> "USER");
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        //todo: заглушка, исправить
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        //todo: заглушка, исправить
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
