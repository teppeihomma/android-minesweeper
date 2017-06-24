package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.entity.GameBoardEntity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MemoryCacheImpl implements MemoryCache {

    private static final String KEY_GAME_BOARD = "game_board";

    private final Map<String, Object> datas;

    @Inject
    public MemoryCacheImpl() {
        this.datas = new HashMap<>();
    }

    @Override
    public void putGameBoard(GameBoardEntity gameBoard) {
        this.datas.put(KEY_GAME_BOARD, gameBoard);
    }

    @Override
    public GameBoardEntity getGameBoard() {
        if (this.datas.containsKey(KEY_GAME_BOARD)) {
            return (GameBoardEntity) this.datas.get(KEY_GAME_BOARD);
        }
        return null;
    }
}
