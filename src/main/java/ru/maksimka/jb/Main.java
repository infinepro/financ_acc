package ru.maksimka.jb;

import ru.maksimka.jb.view.ViewService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        System.out.print("\tПриветствую вас !\n" +
                "|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|" +
                "-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|" + "\n" +
                "\tВы уже зарегистрированы? (да/нет) >>>  ");
        ViewService viewService;
        try {
            String tmp = new BufferedReader(new InputStreamReader(System.in)).readLine();
            viewService = new ViewService();
            if (tmp.equals("нет") || tmp.equals("Нет") || tmp.equals("НЕТ")) {
                viewService.ifNotRegistered();
            } else if (tmp.equals("да") || tmp.equals("Да") || tmp.equals("ДА")) {
                while (true) {
                    viewService.ifRegistered();
                }
            } else {
                System.err.println("Некорректный ввод");
                Main.main(new String[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
