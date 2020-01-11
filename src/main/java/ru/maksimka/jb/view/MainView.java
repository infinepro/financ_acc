package ru.maksimka.jb.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainView {

    public void getWelcome() {

        System.out.println("Приветствую вас \n" +
                "Вы уже зарегистрированы? (да/нет)");
        UserViewQuestions userViewQuestions = new UserViewQuestions();
        try {
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();

            if (str.equals("нет")) {
                userViewQuestions.ifNotRegistered();
            } else if (str.equals("да")) {
                while(true) {
                    userViewQuestions.ifRegistered();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
