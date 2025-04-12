// dans com.oxyl.coursepfback.persistance.dao.ZombieDao.java
package com.oxyl.coursepfback.persistance.dao;

import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import java.util.List;

public interface ZombieDao {
    List<ZombieEntity> findAll();
    ZombieEntity findById(Integer id);
    ZombieEntity save(ZombieEntity zombie);
    ZombieEntity update(ZombieEntity zombie);
    boolean delete(Integer id);
    List<ZombieEntity> findByMapId(Integer mapId);
}