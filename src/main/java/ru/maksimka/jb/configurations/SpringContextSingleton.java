package ru.maksimka.jb.configurations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextSingleton {

    private static ApplicationContext context;

    public static ApplicationContext getContext(){
        if(context==null){
            context = new AnnotationConfigApplicationContext("ru.maksimka.jb");
        }
        return context;
    }
}
