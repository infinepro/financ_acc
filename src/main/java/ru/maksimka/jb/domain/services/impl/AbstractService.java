package ru.maksimka.jb.domain.services.impl;

import ru.maksimka.jb.dao.entities.UserEntity;

public class AbstractService {

    protected UserEntity userEntity;

    public AbstractService(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
