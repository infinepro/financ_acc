package ru.maksimka.jb.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.maksimka.jb.DTO.AcctDTO;
import ru.maksimka.jb.SpringContext;
import ru.maksimka.jb.exceptions.*;
import ru.maksimka.jb.services.*;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import static ru.maksimka.jb.SpringContext.*;


public class ViewConsoleService {

    private final String line = "|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|" +
            "-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|";


    private String login;
    private String password;
    private boolean ifSignIn;
    private ApplicationContext context;
    private UserAuthService userAuth;
    private CreateTransactionService transactionService;
    private UserOperationsService operations;

    public ViewConsoleService() {
        this.login = "";
        this.login = "";
        this.context = getContext();
        this.ifSignIn = false;
        this.userAuth = context.getBean(UserAuthService.class);
        this.transactionService = context.getBean(CreateTransactionService.class);
        this.operations = context.getBean(UserOperationsService.class);
    }

    public void ifRegistered() throws IOException {

        if (!ifSignIn) {

            System.out.printf("%-25s", "\tВведите логин  >>> ");
            this.login = new BufferedReader(new InputStreamReader(System.in)).readLine();

            System.out.printf("%-25s", "\tВведите пароль >>> ");
            this.password = new BufferedReader(new InputStreamReader(System.in)).readLine();
        }

        try {
            if (login.isEmpty()) {
                throw new EmptyDataAccesException("Пустой логин недопустим");
            }

            if (password.isEmpty()) {
                throw new EmptyDataAccesException("Пустой пароль недопустим");
            }

            if (ifSignIn || userAuth.authUser(login, password) != -1) {
                ifSignIn = true;
                operations.setLogin(login);

                System.out.print(line + "\n\tДоступ к вашим операциям открыт\n" + line + "" +
                        "\n\tВы можете использовть одну из четырёх операций: \n" +
                        "\t\t0 - Выход из программы\n" +
                        "\t\t1 - Отобразить ваши счета\n" +
                        "\t\t2 - Создать новый счет\n" +
                        "\t\t3 - Добавить новую категорию транзакции(расходов или доходов)\n" +
                        "\t\t4 - Добавить новое наименование счета\n" +
                        "\t\t5 - Перевести деньги на другой счет (транзакция)\n" +
                        "\t\t>>>\t");

                String numberOperation = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if (!(numberOperation.equals("0") ||
                        numberOperation.equals("1") ||
                        numberOperation.equals("2") ||
                        numberOperation.equals("3") ||
                        numberOperation.equals("4") ||
                        numberOperation.equals("5"))) {
                    System.err.println("Некорректный ввод, попробуйте снова\n");
                    ifRegistered();
                }

                switch (Integer.parseInt(numberOperation)) {

                    //Exit program
                    case 0: {
                        System.out.println("ДОСВИДОС!!!");
                        System.exit(0);
                    }

                    //View your acct
                    case 1: {
                        System.out.println(line);
                        List<AcctDTO> accts = operations.getAllAcct();

                        if (accts.isEmpty()) {
                            System.err.println("\tУ вас нету счетов, вы можете завести новый");
                            ifRegistered();
                        }

                        int count = 1;

                        for (AcctDTO acctDTO : accts) {
                            System.out.printf("%-4s%-30s%10s%5s%n", (count++ + "."), acctDTO.getNameAcct(), acctDTO.getBalance(), "руб.");
                        }

                        break;
                    }

                    //Create new acct
                    case 2: {
                        System.out.println(line);
                        List<String> listTypeAccts = operations.getAllTypeAccts(context.getBean(DataSource.class));

                        for (int i = 0; i < listTypeAccts.size(); i++) {
                            System.out.println("\t" + " (" + (i + 1) + ") " + listTypeAccts.get(i));
                        }

                        System.out.printf("%-35s", "\n\tКакой счёт хотите добавить >>>");
                        Integer numberAcct;
                        BigDecimal balance;

                        try {
                            numberAcct = Integer.parseInt(
                                    new BufferedReader(new InputStreamReader(System.in)).readLine());
                            System.out.printf("%-34s", "\tТекущий баланс счёта       >>>");
                            balance = new BigDecimal(
                                    new BufferedReader(new InputStreamReader(System.in)).readLine());
                        } catch (NumberFormatException e) {
                            System.err.println("\tНеправильный формат ввода");
                            ifRegistered();
                            break;
                        }

                        if (operations.addNewAcct(login, balance, numberAcct)) {
                            System.out.println(line + "\n\tСчёт успешно создан!");
                        } else {
                            System.out.println(line + "\n\tСчёт не добавлен, свяжитесь с админом!");
                        }

                        break;
                    }

                    //add new category transaction
                    case 3: {
                        System.out.println(line);
                        System.out.print("\tВведите имя нового типа транзакции >>> ");
                        String nameType = new BufferedReader(new InputStreamReader(System.in)).readLine();

                        if (operations.addNewTypeTransaction(nameType)) {
                            System.out.println(line);
                            System.out.println("\tНовый тип транзакции создан");
                        } else {
                            System.out.println(line);
                            System.err.println("\tНовый тип транзакции не добавлен, свяжитесь с админом!");
                        }

                        break;
                    }

                    //add new name acct's
                    case 4: {
                        System.out.println(line);
                        System.out.print("\tВведите имя нового типа счета >>> ");
                        String nameType = new BufferedReader(new InputStreamReader(System.in)).readLine();

                        if (operations.addNewTypeAcct(nameType)) {
                            System.out.println(line);
                            System.out.println("\tНовый тип счета создан");
                        } else {
                            System.out.println(line);
                            System.err.println("\tНовый тип счета не добавлен, свяжитесь с админом!");
                        }

                        break;
                    }

                    //create transaction fromId -> toId
                    case 5: {
                        int fromAcct;
                        int toAcct;
                        BigDecimal sum;
                        //CreateTransactionService transactionService;

                        System.out.println(line);
                        List<AcctDTO> accts = operations.getAllAcct();

                        if (accts.isEmpty()) {
                            System.err.println("\tУ вас нету счетов, вы можете завести новый");
                            ifRegistered();
                        } else if (accts.size() < 2) {
                            System.err.println("\tУ вас только один счет, вы можете завести ещё");
                            ifRegistered();
                        }

                        int count = 1;

                        for (AcctDTO acctDTO : accts) {
                            System.out.printf("%-4s%-30s%10s%5s%n",
                                    ("\t" + count++ + "."),
                                    ("(" + acctDTO.getId() + ") " + acctDTO.getNameAcct()),
                                    acctDTO.getBalance(),
                                    "руб.");
                        }

                        try {
                            System.out.print("\tНомер счета для списания   >>>  ");
                            fromAcct = Integer.parseInt(
                                    new BufferedReader(new InputStreamReader(System.in)).readLine());
                            if (fromAcct > accts.size()) {
                                throw new NumberFormatException();
                            }

                            System.out.print("\tНомер счета для пополнения >>>  ");
                            toAcct = Integer.parseInt(
                                    new BufferedReader(new InputStreamReader(System.in)).readLine());
                            if (toAcct > accts.size()) {
                                throw new NumberFormatException();
                            }

                            System.out.print("\tСумма транзакции >>>  ");
                            sum = new BigDecimal(
                                    new BufferedReader(new InputStreamReader(System.in)).readLine());
                            if (sum.compareTo(accts.get(fromAcct - 1).getBalance()) == 1) {
                                throw new NumberFormatException("Нехватает средств на счете");
                            }

                            try {
                                transactionService.createTransaction(accts.get(fromAcct - 1).getId(), accts.get(toAcct - 1).getId(), sum);
                            } catch (TransactionFailException e) {
                                e.printStackTrace();
                            }

                        } catch (NumberFormatException e) {
                            System.out.println(line);
                            System.err.println("\tНекорректное значение");
                            break;
                        }
                        System.out.println(line);
                        System.out.println("\tТранзакция выполнена");
                        break;
                    }

                    default:
                        break;
                }

                ifRegistered();
            }

        } catch (WrongUserPasswordException e) {
            System.out.println(line);
            ifRegistered();
        }

        catch (EmptyDataAccesException e) {
            System.out.println(line);
            ifRegistered();
        }
    }

    public void ifNotRegistered() throws IOException {
        while (true) {
            System.out.println(line);
            System.out.printf("%-25s", "\tВведите логин  >>> ");
            String login = new BufferedReader(new InputStreamReader(System.in)).readLine();

            System.out.printf("%-25s", "\tВведите email  >>> ");
            String email = new BufferedReader(new InputStreamReader(System.in)).readLine();

            System.out.printf("%-25s", "\tВведите пароль >>> ");
            String password = new BufferedReader(new InputStreamReader(System.in)).readLine();

            try {
                if (userAuth.registerUser(login, password, email) != -1) {
                    System.out.println(line + "\n" + "\tВы успешно зарегистрировались, можете войти в стистему");
                    ifRegistered();
                }
            } catch (LoginBusyException e) {
                ifNotRegistered();
            }
        }
    }
}
