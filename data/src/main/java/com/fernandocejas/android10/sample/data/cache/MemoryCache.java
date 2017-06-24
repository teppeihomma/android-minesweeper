package com.fernandocejas.android10.sample.data.cache;

import com.fernandocejas.android10.sample.data.entity.GameBoardEntity;

public interface MemoryCache {

    void putGameBoard(GameBoardEntity gameBoard);

    GameBoardEntity getGameBoard();
}
