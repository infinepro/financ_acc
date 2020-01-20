package ru.maksimka.jb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContainerContext {

    private static ApplicationContext context;

    public static ApplicationContext coontext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext("ru.maksimka.jb");
        } return context;
    }
}
