package ru.maksimka.jb;

import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.entities.UserEntity;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;

import static ru.maksimka.jb.SpringContext.*;

public class Main {

    public static void main(String[] args) {

        UserEntity user = new UserEntity()
                .withName("newName")
                .withEmail("test@mail")
                .withPassword("new Password");

        UserDao userDao = getContext().getBean(UserDao.class);

        try {
            userDao.insert(user);

        } catch ( AlreadyExistsException e) {
            e.printStackTrace();
        }

    }
}
