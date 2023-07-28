package org.rangiffler.db.dao;

import org.rangiffler.db.ServiceDB;
import org.rangiffler.db.entity.CountryEntity;
import org.rangiffler.db.jpa.EmfProvider;
import org.rangiffler.db.jpa.JpaTransactionManager;

import javax.annotation.Nonnull;
import java.util.List;

public class RangifflerGeoDAOHibernate extends JpaTransactionManager implements RangifflerGeoDAO{

    public RangifflerGeoDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.RANGIFFLER_GEO).createEntityManager());
    }

    @Nonnull
    @Override
    public List<CountryEntity> findAll() {
        return em.createQuery("select c from CountryEntity c", CountryEntity.class).getResultList();
    }

    @Override
    public CountryEntity findByCode(String code) {
        return em.createQuery("select c from CountryEntity c where code=:code", CountryEntity.class)
                .setParameter("code", code)
                .getSingleResult();
    }

    @Override
    public CountryEntity findByName(String name) {
        return em.createQuery("select c from CountryEntity c where name=:name", CountryEntity.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
