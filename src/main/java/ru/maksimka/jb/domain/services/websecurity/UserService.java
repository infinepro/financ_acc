package ru.maksimka.jb.domain.services.websecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.maksimka.jb.domain.dto.UserDto;

@Service
public class UserService implements UserDetailsService {

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);

        return new UserDto()
                .withUsername("admin")
                .withPassword(encoder.encode("admin"))
                .withAccountNonExpired(true)
                .withAccountNonLocked(true)
                .withCredentialsNonExpired(true)
                .withEnabled(true);

    }
}
