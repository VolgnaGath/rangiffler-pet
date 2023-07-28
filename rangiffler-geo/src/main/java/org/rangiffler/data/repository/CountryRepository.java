package org.rangiffler.data.repository;

import jakarta.annotation.Nonnull;
import org.rangiffler.data.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    @Nonnull
    List<CountryEntity> findAll();

    CountryEntity findByCode(String code);
    CountryEntity findByName(String name);
}
