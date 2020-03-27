package ru.maksimka.jb.ui._console_bad_code;

import org.springframework.stereotype.Service;
import ru.maksimka.jb.dao.daoimpl.UserDao;
import ru.maksimka.jb.domain.dto.UserDto;
import ru.maksimka.jb.domain.services.console_services.ServiceAuthorization;
import ru.maksimka.jb.domain.services.assistants.ValidationData;
import ru.maksimka.jb.exceptions.*;

import java.io.IOException;

import static ru.maksimka.jb.ui._console_bad_code.StatusAuthorization.AUTH;
import static ru.maksimka.jb.ui._console_bad_code.StatusAuthorization.REGISTERED;

@Service
public class ForUnauthorizedViewConsole extends ViewConsoleHelper {

    private StatusAuthorization status;
    private ValidationData valid;

    private UserDao userDao;
    private UserDto userDto;
    private ServiceAuthorization serviceAuthorizationService;
    private ForAuthorizedViewConsole forAuthorizedViewConsole;

    protected ForUnauthorizedViewConsole() {
        this.status = StatusAuthorization.HAS_NO_STATUS;
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
                        .withUsername(login)
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
                serviceAuthorizationService.checkUser(new UserDto().withUsername(login).withPassword(password));

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
