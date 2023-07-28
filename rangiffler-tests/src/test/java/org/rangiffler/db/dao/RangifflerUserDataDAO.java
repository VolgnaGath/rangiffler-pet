package org.rangiffler.db.dao;


import org.rangiffler.db.entity.FriendsEntity;
import org.rangiffler.db.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface RangifflerUserDataDAO {
    List<UserEntity> getAllFriends(UUID uuid);

}