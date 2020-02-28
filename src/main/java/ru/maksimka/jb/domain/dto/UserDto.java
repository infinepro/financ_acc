package ru.maksimka.jb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.maksimka.jb.domain.dto.roles.Roles;

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
    private String username;
    private String password;
    private String email;

    //todo: перенести в базу заглушки если потребуется..пересмотреть
    //for spring security
    private Collection<GrantedAuthority> grantedAuthorityList;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //todo: заглушка, исправить
        GrantedAuthority grantedAuthority = new Roles();
        grantedAuthorityList.add(grantedAuthority);
        return grantedAuthorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        //todo: заглушка, исправить
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        //todo: заглушка, исправить
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
