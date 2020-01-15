package ru.maksimka.jb.DTO;

import ru.maksimka.jb.containers.Acct;
import ru.maksimka.jb.containers.User;

public class ConverterIntoDTO {

    public AcctDTO convertUser(Acct acct) {
        AcctDTO acctDTO = new AcctDTO(acct.getUserName(), acct.getBalance());
        return acctDTO;
    }

}
