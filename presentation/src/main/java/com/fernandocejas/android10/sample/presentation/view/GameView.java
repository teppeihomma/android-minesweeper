package com.fernandocejas.android10.sample.presentation.view;

import com.fernandocejas.android10.sample.domain.GameBoard;

public interface GameView {

    void navigateToStart();
    void showGameOver();
    void showGameClear();
    void updateGameBoard(GameBoard gameBoard);
}
