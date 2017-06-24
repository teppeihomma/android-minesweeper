package com.fernandocejas.android10.sample.domain.repository;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.Level;

import java.util.List;

import io.reactivex.Observable;

public interface GameRepository {

    Observable<GameBoard> initialize(int w, int h, int mines);

    Observable<GameBoard> open(int x, int y);

    Observable<GameBoard> flag(int x, int y);

    List<Level> getLevels();
}
