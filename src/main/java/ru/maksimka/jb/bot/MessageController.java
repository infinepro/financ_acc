package ru.maksimka.jb.bot;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MessageController {

    private HashMap<String, String> responses = new HashMap<String, String>() {{
        put("Привет", "Привет друг");
        put("Как дела", "Все отлично, а у тебя?))");
    }};

    public String getMessage(String req){
        if ( !responses.containsKey(req)) {
            return "Я не понимаю....";
        } return responses.get(req);
    }

}
