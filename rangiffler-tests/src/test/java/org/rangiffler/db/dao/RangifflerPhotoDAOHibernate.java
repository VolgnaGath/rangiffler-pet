package org.rangiffler.db.dao;

import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.PhotoEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

import java.util.List;

public class RangifflerPhotoDAOHibernate extends JpaTransactionManager implements RangifflerPhotoDAO{

    public RangifflerPhotoDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_PHOTO).createEntityManager());
    }

    @Override
    public List<PhotoEntity> findAll() {
        return em.createQuery("select p from PhotoEntity p", PhotoEntity.class).getResultList();
    }

    @Override
    public int addPhoto(PhotoEntity photo) {
        persist(photo);
        return 0;
    }

    @Override
    public List<PhotoEntity> findAllByUsername(String username) {
        return em.createQuery("select p from PhotoEntity p where username = :username", PhotoEntity.class)
                .setParameter("username", username).getResultList();
    }


}
