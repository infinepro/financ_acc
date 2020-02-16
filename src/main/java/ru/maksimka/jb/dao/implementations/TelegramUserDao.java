package ru.maksimka.jb.dao.implementations;

import ru.maksimka.jb.dao.Dao;
import ru.maksimka.jb.dao.dao_entities.TelegramUserEntity;

import java.util.List;

public class TelegramUserDao implements Dao<Long , TelegramUserEntity> {

    @Override
    public Long findBy(TelegramUserEntity telegramUserEntity) throws Exception {
        return null;
    }

    @Override
    public List<Long> findByAll() throws Exception {
        return null;
    }

    @Override
    public Long insert(Long aLong) throws Exception {
        return null;
    }

    @Override
    public Long update(Long aLong) throws Exception {
        return null;
    }

    @Override
    public boolean delete(TelegramUserEntity parameter) throws Exception {
        return false;
    }
}
