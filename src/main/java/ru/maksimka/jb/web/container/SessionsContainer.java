package ru.maksimka.jb.web.container;

import java.util.HashMap;

public class SessionsContainer {
    private static HashMap<String, String> sessions;

    public static HashMap<String, String> getSessions(){
        if (sessions == null) {
            sessions = new HashMap<>();
        } return sessions;
    }
}
