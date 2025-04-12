// dans com.oxyl.coursepfback.core.service.ZombieService.java
package com.oxyl.coursepfback.core.service;

import com.oxyl.coursepfback.core.model.Zombie;
import com.oxyl.coursepfback.persistance.dao.ZombieDao;
import com.oxyl.coursepfback.persistance.entity.ZombieEntity;
import com.oxyl.coursepfback.persistance.mapper.ZombieEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZombieService {

    private final ZombieDao zombieDao;

    @Autowired
    public ZombieService(ZombieDao zombieDao) {
        this.zombieDao = zombieDao;
    }

    public List<Zombie> getAllZombies() {
        return zombieDao.findAll().stream()
                .map(ZombieEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    public Zombie getZombieById(Integer id) {
        ZombieEntity entity = zombieDao.findById(id);
        return entity != null ? ZombieEntityMapper.toModel(entity) : null;
    }

    public Zombie createZombie(Zombie zombie) {
        ZombieEntity entity = ZombieEntityMapper.toEntity(zombie);
        ZombieEntity savedEntity = zombieDao.save(entity);
        return ZombieEntityMapper.toModel(savedEntity);
    }

    public Zombie updateZombie(Integer id, Zombie zombie) {
        zombie.setIdZombie(id);
        ZombieEntity entity = ZombieEntityMapper.toEntity(zombie);
        ZombieEntity updatedEntity = zombieDao.update(entity);
        return updatedEntity != null ? ZombieEntityMapper.toModel(updatedEntity) : null;
    }

    public boolean deleteZombie(Integer id) {
        return zombieDao.delete(id);
    }

    public List<Zombie> getZombiesByMapId(Integer mapId) {
        return zombieDao.findByMapId(mapId).stream()
                .map(ZombieEntityMapper::toModel)
                .collect(Collectors.toList());
    }
}