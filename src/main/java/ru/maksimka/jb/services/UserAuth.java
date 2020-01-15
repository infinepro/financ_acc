package ru.maksimka.jb.services;

public interface UserAuth<L, P> {

    boolean authUser(L login, L password);
    boolean registerUser(L login, L password,  L email);

}
