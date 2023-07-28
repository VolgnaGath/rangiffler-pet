package org.rangiffler.db.dao;

import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.FriendsEntity;
import org.rangiffler.db.entity.UserEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

import java.util.List;
import java.util.UUID;

public class RangifflerUserDataDAOHibernate extends JpaTransactionManager implements RangifflerUserDataDAO {

    public RangifflerUserDataDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_USERDATA).createEntityManager());
    }

    @Override
    public List<UserEntity> getAllFriends(UUID uuid) {
        return em.createQuery("select u from UserEntity f where user_id = :id", UserEntity.class)
                .setParameter("id", uuid).getResultList();
    }
}
