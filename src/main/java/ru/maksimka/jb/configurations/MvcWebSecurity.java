package ru.maksimka.jb.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class MvcWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;





    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                   // .antMatchers("/** ").permitAll()
                    .antMatchers("/app/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/auth");


    }
}
