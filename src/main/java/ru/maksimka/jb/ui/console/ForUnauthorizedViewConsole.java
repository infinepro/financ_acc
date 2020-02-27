package ru.maksimka.jb.ui.console;

import org.springframework.stereotype.Component;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.exceptions.*;
import ru.maksimka.jb.domain.services.ServiceAuthorization;
import ru.maksimka.jb.domain.services.assistants.ValidationData;

import java.io.IOException;

import static ru.maksimka.jb.configurations.SpringContextSingleton.getContext;
import static ru.maksimka.jb.ui.console.StatusAuthorization.AUTH;
import static ru.maksimka.jb.ui.console.StatusAuthorization.REGISTERED;

@Component
public class ForUnauthorizedViewConsole extends ViewConsoleHelper {

    private StatusAuthorization status;
    private ValidationData valid;

    private UserDao userDao;
    private UserDto userDto;
    private ServiceAuthorization serviceAuthorizationService;
    private ForAuthorizedViewConsole forAuthorizedViewConsole;

    protected ForUnauthorizedViewConsole(ValidationData valid, UserDao userDao, ServiceAuthorization serviceAuthorizationService) {
        this.status = StatusAuthorization.HAS_NO_STATUS;
        this.valid = valid;
        this.userDao = userDao;
        this.serviceAuthorizationService = serviceAuthorizationService;
    }

    private void setStatus(StatusAuthorization statusAuthorization) {
        this.status = statusAuthorization;

    }

    private StatusAuthorization getStatus() {
        return this.status;
    }

    protected void getAuthView(StatusAuthorization statusAuthorization) {
        setStatus(statusAuthorization);

        if (getStatus() == StatusAuthorization.NOT_REGISTERED) {
            registrationView();
            getAuthView(getStatus());
        } else if (getStatus() == REGISTERED) {
            signInView();
            getAuthView(getStatus());
        } else if (getStatus() == AUTH) {
            forAuthorizedViewConsole = getContext().getBean(ForAuthorizedViewConsole.class);

            try {
                forAuthorizedViewConsole.showUserOptions(this.serviceAuthorizationService.getService());
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

                serviceAuthorizationService.registration(new UserDto()
                        .withName(login)
                        .withEmail(email)
                        .withPassword(password));
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
                serviceAuthorizationService.checkUser(new UserDto().withName(login).withPassword(password));

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
