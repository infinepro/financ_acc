package ru.maksimka.jb.console_views;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotValidDataException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.services.AuthStatus;
import ru.maksimka.jb.services.UserService;
import ru.maksimka.jb.services.ValidationInputData;

import java.io.IOException;

import static ru.maksimka.jb.configurations.SpringContext.*;
import static ru.maksimka.jb.services.AuthStatus.AUTH;
import static ru.maksimka.jb.services.AuthStatus.REGISTERED;

@Service
public class ForUnauthorizedViewConsole extends ViewConsoleHelper {

    private AuthStatus status;
    private ValidationInputData valid;
    private UserDao userDao;
    private UserService userService;
    private ForAuthorizedViewConsole forAuthorizedViewConsole;

    //CONSTRUCTOR

    protected ForUnauthorizedViewConsole(ValidationInputData valid, UserDao userDao, UserService userService) {
        this.status = AuthStatus.HAS_NO_STATUS;
        this.valid = valid;
        this.userDao = userDao;
        this.userService = userService;
    }

    private void setStatus(AuthStatus authStatus) {
        this.status = authStatus;

    }

    private AuthStatus getStatus() {
        return this.status;
    }

    public void getAuthView(AuthStatus authStatus) {
        setStatus(authStatus);

        if (getStatus() == AuthStatus.NOT_REGISTERED) {
            registration();
            getAuthView(getStatus());
        } else if (getStatus() == REGISTERED) {
            signIn();
            getAuthView(getStatus());
        } else if (getStatus() == AUTH) {
            forAuthorizedViewConsole = getContext().getBean(ForAuthorizedViewConsole.class);
            forAuthorizedViewConsole.showUserOptions();
        }

    }

    private void registration() {
        UserDto userDto = getContext().getBean(UserDto.class);
        ValidationInputData valid = getContext().getBean(ValidationInputData.class);

        try {
            while (true) {
                printLine();
                print("\tВведите логин\n");
                print("\t>>>>>  ");
                userDto.setName(readStringFromConsole());

                print("\tВведите email\n");
                print("\t>>>>>  ");
                try {
                    userDto.setEmail(valid.emailValidation(readStringFromConsole()));
                } catch (NotValidDataException e) {
                    printErr("\tнекорректный email");
                    continue;
                }

                print("\tВведите пароль\n");
                print("\t>>>>>  ");
                try {
                    userDto.setPassword(valid.passwordValidation(readStringFromConsole()));
                } catch (NotValidDataException e) {
                    printErr("\tнекорректный password");
                    continue;
                }
                break;
            }

            try {
                userService.registration(userDto);
                setStatus(REGISTERED);
                printLine();
                print("\tВы успешно зарегистрировались");
            } catch (AlreadyExistsException e) {
                e.showMessage();
                registration();
            }
        } catch (IOException e) {
            userDto = null;
            registration();
        }
    }

    private void signIn() {
        UserDto userDto = getContext().getBean(UserDto.class);

        while (true) {
            try {
                printLine();
                print("\tВведите логин\n");
                print("\t>>>>>  ");
                userDto.setName(readStringFromConsole());

                print("\tВведите пароль\n");
                print("\t>>>>>  ");
                userDto.setPassword(readStringFromConsole());
                userService.signIn(userDto);
                setStatus(AUTH);
                printLine();
                print("\tВы успешно авторизовались");
                printLine();
                break;

            } catch (IOException e) {
                printErr("\tнепредвиденная ошибка");
                signIn();
            } catch (RecordNotFoundException | WrongUserPasswordException e) {
                printErr("\tневерный логин или пароль");
                signIn();
            }
        }
    }
}
