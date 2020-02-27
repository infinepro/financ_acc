package ru.maksimka.jb.domain.services;

import ru.maksimka.jb.domain.services.impl.ServiceAccounts;
import ru.maksimka.jb.domain.services.impl.ServiceTransactions;
import ru.maksimka.jb.domain.services.impl.ServiceUser;

public interface MainService extends ServiceAccounts, ServiceUser, ServiceTransactions {

}
