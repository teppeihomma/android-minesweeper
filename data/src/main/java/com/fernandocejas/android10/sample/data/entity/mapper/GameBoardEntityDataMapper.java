package com.fernandocejas.android10.sample.data.entity.mapper;

import com.fernandocejas.android10.sample.data.entity.GameBoardEntity;
import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.GameStatus;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameBoardEntityDataMapper {

    @Inject
    public GameBoardEntityDataMapper() {
    }

    public GameBoard transform(GameBoardEntity entity) {
        GameBoard gameBoard = null;
        if (entity != null) {
            gameBoard = new GameBoard();
            gameBoard.setWidth(entity.getWidth());
            gameBoard.setHeight(entity.getHeight());
            gameBoard.setField(entity.getField());
            gameBoard.setFieldStatuses(entity.getFieldStatuses());
            gameBoard.setStatus(GameStatus.valueOf(entity.getStatus().name()));
            gameBoard.setMines(entity.getMines());
        }
        return gameBoard;
    }
}
