package ru.maksimka.jb.console_views;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.implementations.UserDao;
import ru.maksimka.jb.dto.UserDto;
import ru.maksimka.jb.exceptions.AlreadyExistsException;
import ru.maksimka.jb.exceptions.NotValidDataException;
import ru.maksimka.jb.exceptions.RecordNotFoundException;
import ru.maksimka.jb.exceptions.WrongUserPasswordException;
import ru.maksimka.jb.services.AuthStatus;
import ru.maksimka.jb.services.ServiceUsers;
import ru.maksimka.jb.services.Services;
import ru.maksimka.jb.services.ValidationData;

import java.io.IOException;

import static ru.maksimka.jb.configurations.SpringContext.*;
import static ru.maksimka.jb.services.AuthStatus.AUTH;
import static ru.maksimka.jb.services.AuthStatus.REGISTERED;

@Service
public class ForUnauthorizedViewConsole extends ViewConsoleHelper {

    private AuthStatus status;
    private ValidationData valid;

    private UserDao userDao;
    private UserDto userDto;
    private Services serviceUsers;
    private ForAuthorizedViewConsole forAuthorizedViewConsole;

    //CONSTRUCTOR

    protected ForUnauthorizedViewConsole(ValidationData valid, UserDao userDao, Services serviceUsers) {
        this.status = AuthStatus.HAS_NO_STATUS;
        this.valid = valid;
        this.userDao = userDao;
        this.serviceUsers = serviceUsers;
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
            forAuthorizedViewConsole.showUserOptions(this.serviceUsers);
        }
    }

    private void registration() {
        //UserDto userDto = getContext().getBean(UserDto.class);
        ValidationData valid = getContext().getBean(ValidationData.class);
        String login;
        String email;
        String password;

        try {
            while (true) {
                printLine();
                print("\tВведите логин\n");
                print("\t>>>>>  ");
                login = readStringFromConsole();

                print("\tВведите email\n");
                print("\t>>>>>  ");
                try {
                    email = valid.emailValidation(readStringFromConsole());
                } catch (NotValidDataException e) {
                    printErr("\tнекорректный email");
                    continue;
                }

                print("\tВведите пароль\n");
                print("\t>>>>>  ");
                try {
                    password = valid.passwordValidation(readStringFromConsole());
                } catch (NotValidDataException e) {
                    printErr("\tнекорректный password");
                    continue;
                }
                break;
            }

            try {
                serviceUsers.registration(login, email, password);
                setStatus(REGISTERED);
                printLine();
                print("\tВы успешно зарегистрировались");
            } catch (AlreadyExistsException e) {
                e.showMessage();
                registration();
            }
        } catch (IOException e) {
            registration();
        }
    }

    private void signIn() {
        this.userDto = getContext().getBean(UserDto.class);
        String login;
        String password;

        while (true) {
            try {
                printLine();
                print("\tВведите логин\n");
                print("\t>>>>>  ");
                login = readStringFromConsole();

                print("\tВведите пароль\n");
                print("\t>>>>>  ");
                password = readStringFromConsole();
                serviceUsers.signIn(login, password);

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
