package org.rangiffler.db.dao;

import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.AuthorityEntity;
import org.rangiffler.db.entity.UserAuthEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

public class RangifflerUsersDAOHibernate extends JpaTransactionManager implements RangifflerUsersDAO {

    public RangifflerUsersDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_AUTH).createEntityManager());
    }

    @Override
    public int createUser(UserAuthEntity user) {
        user.setPassword(pe.encode(user.getPassword()));
        persist(user);
        return 0;
    }

    @Override
    public String getUserId(String userName) {
        return em.createQuery("select u from UserEntity u where username=:username", UserAuthEntity.class)
                .setParameter("username", userName)
                .getSingleResult()
                .getId()
                .toString();
    }

    @Override
    public int removeUser(UserAuthEntity user) {
        remove(user);
        return 0;
    }

    @Override
    public int updateUser(UserAuthEntity user, AuthorityEntity authority) {
        merge(authority);
        merge(user);
        return 0;
    }

    @Override
    public UserAuthEntity getUser(UserAuthEntity user) {
        return em.createQuery("select u from UserEntity u where username = :username", UserAuthEntity.class)
                .setParameter("username", user.getUsername()).getSingleResult();
    }
}