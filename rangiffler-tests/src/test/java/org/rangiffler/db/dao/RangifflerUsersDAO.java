package org.rangiffler.db.dao;

import org.rangiffler.db.entity.AuthorityEntity;
import org.rangiffler.db.entity.UserAuthEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface RangifflerUsersDAO {

    PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    int createUser(UserAuthEntity user);

    String getUserId(String userName);

    int removeUser(UserAuthEntity user);
    int updateUser(UserAuthEntity user, AuthorityEntity authority);
    UserAuthEntity getUser(UserAuthEntity user);

}