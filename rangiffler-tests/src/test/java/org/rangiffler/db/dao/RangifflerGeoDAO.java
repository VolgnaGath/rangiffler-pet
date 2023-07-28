package org.rangiffler.db.dao;

import org.rangiffler.db.entity.CountryEntity;

import javax.annotation.Nonnull;
import java.util.List;

public interface RangifflerGeoDAO {
    @Nonnull
    List<CountryEntity> findAll();

    CountryEntity findByCode(String code);
    CountryEntity findByName(String name);
}
