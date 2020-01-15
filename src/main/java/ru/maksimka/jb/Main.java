package ru.maksimka.jb;


import ru.maksimka.jb.view.ViewService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        System.out.println("Приветствую вас \n" +
                             "Вы уже зарегистрированы? (да/нет)");
        ViewService viewService;
        try {
            String tmp = new BufferedReader(new InputStreamReader(System.in)).readLine();
            viewService = new ViewService();
            if (tmp.equals("нет")) {
                viewService.ifNotRegistered();
            } else if (tmp.equals("да")) {
                while(true) {
                    viewService.ifRegistered();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
