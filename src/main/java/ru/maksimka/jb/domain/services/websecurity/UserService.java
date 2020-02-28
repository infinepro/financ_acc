package ru.maksimka.jb.domain.services.websecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.ServiceAuthorization;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

@Service
public class UserService implements UserDetailsService {

    //private PasswordEncoder encoder;
    private ServiceAuthorization serviceAuthorization;

    public UserService(ServiceAuthorization serviceAuthorization) {
        this.serviceAuthorization = serviceAuthorization;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDto userDto;

        try {
            userDto = serviceAuthorization.checkUser(username);
        } catch (RecordNotFoundException e) {
            throw new UsernameNotFoundException("User with name:" + username + "not found!");
        }

        System.out.println(userDto);

        userDto.setAccountNonExpired(true);
        userDto.setAccountNonLocked(true);
        userDto.setCredentialsNonExpired(true);
        userDto.setEnabled(true);
        return userDto;
    }
}
