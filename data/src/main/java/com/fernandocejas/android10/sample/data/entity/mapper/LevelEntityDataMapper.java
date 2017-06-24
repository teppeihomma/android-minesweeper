package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.LevelEntity;
import com.fernandocejas.android10.sample.domain.Level;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LevelEntityDataMapper {

    @Inject
    public LevelEntityDataMapper() {
    }

    public Level transform(LevelEntity entity) {
        Level level = null;
        if (entity != null) {
            level = new Level();
            level.setLevel(entity.getLevel());
            level.setWidth(entity.getWidth());
            level.setHeight(entity.getHeight());
            level.setMines(entity.getMines());
        }
        return level;
    }
}
