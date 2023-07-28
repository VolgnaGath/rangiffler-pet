package org.rangiffler.db.dao;

import org.rangiffler.db.entity.PhotoEntity;

import java.util.List;

public interface RangifflerPhotoDAO {
    List<PhotoEntity> findAll();
    int addPhoto(PhotoEntity photo);
    List<PhotoEntity> findAllByUsername(String username);
}
