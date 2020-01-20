package ru.maksimka.jb.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import({UserAuth.class, UserOperations.class,CreateTransactionService.class})
@Configuration
public class ServiceConfiguration {

}
