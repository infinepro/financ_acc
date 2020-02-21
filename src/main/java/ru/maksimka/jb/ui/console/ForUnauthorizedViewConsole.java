package ru.maksimka.jb.ui.console;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.exceptions.*;
import ru.maksimka.jb.domain.services.Auth;
import ru.maksimka.jb.domain.services.AuthStatus;
import ru.maksimka.jb.domain.services.ValidationData;

import java.io.IOException;

import static ru.maksimka.jb.configurations.SpringContextSingleton.getContext;
import static ru.maksimka.jb.domain.services.AuthStatus.AUTH;
import static ru.maksimka.jb.domain.services.AuthStatus.REGISTERED;

@Component
public class ForUnauthorizedViewConsole extends ViewConsoleHelper {

    private AuthStatus status;
    private ValidationData valid;

    private UserDao userDao;
    private UserDto userDto;
    private Auth authService;
    private ForAuthorizedViewConsole forAuthorizedViewConsole;

    protected ForUnauthorizedViewConsole(ValidationData valid, UserDao userDao, Auth authService) {
        this.status = AuthStatus.HAS_NO_STATUS;
        this.valid = valid;
        this.userDao = userDao;
        this.authService = authService;
    }

    private void setStatus(AuthStatus authStatus) {
        this.status = authStatus;

    }

    private AuthStatus getStatus() {
        return this.status;
    }

    protected void getAuthView(AuthStatus authStatus) {
        setStatus(authStatus);

        if (getStatus() == AuthStatus.NOT_REGISTERED) {
            registrationView();
            getAuthView(getStatus());
        } else if (getStatus() == REGISTERED) {
            signInView();
            getAuthView(getStatus());
        } else if (getStatus() == AUTH) {
            forAuthorizedViewConsole = getContext().getBean(ForAuthorizedViewConsole.class);

            try {
                forAuthorizedViewConsole.showUserOptions(this.authService.getService());
            } catch (NotAuthorizedException e) {
                signInView();
                getAuthView(getStatus());
                e.printStackTrace();
            }
        }
    }

    private void registrationView() {
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
                authService.registration(login, email, password);
                setStatus(REGISTERED);
                printLine();
                print("\tВы успешно зарегистрировались");
            } catch (AlreadyExistsException e) {
                e.showMessage();
                registrationView();
            }
        } catch (IOException e) {
            registrationView();
        }
    }

    private void signInView() {
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
                authService.signIn(login, password);

                setStatus(AUTH);
                printLine();
                print("\tВы успешно авторизовались");
                printLine();
                getAuthView(getStatus());

            } catch (IOException e) {
                printErr("\tнепредвиденная ошибка");
                signInView();
            } catch (RecordNotFoundException | WrongUserPasswordException e) {
                printErr("\tневерный логин или пароль");
                signInView();
            }
        }
    }
}
