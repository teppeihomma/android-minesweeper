/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.android10.sample.presentation.presenter;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.Level;
import com.fernandocejas.android10.sample.domain.interactor.DefaultObserver;
import com.fernandocejas.android10.sample.domain.interactor.GameFlag;
import com.fernandocejas.android10.sample.domain.interactor.GameInitialize;
import com.fernandocejas.android10.sample.domain.interactor.GameOpen;
import com.fernandocejas.android10.sample.domain.repository.GameRepository;
import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.view.GameView;

import java.util.List;

import javax.inject.Inject;

@PerActivity
public class GamePresenter implements Presenter {

    private GameView gameView;

    private final GameInitialize gameInitializeUseCase;
    private final GameOpen gameOpenUseCase;
    private final GameFlag gameFlagUseCase;
    private final GameRepository gameRepository;

    @Inject
    public GamePresenter(
            GameInitialize gameInitializeUseCase,
            GameOpen gameOpenUseCase,
            GameFlag gameFlagUseCase,
            GameRepository gameRepository) {
        this.gameInitializeUseCase = gameInitializeUseCase;
        this.gameOpenUseCase = gameOpenUseCase;
        this.gameFlagUseCase = gameFlagUseCase;
        this.gameRepository = gameRepository;
    }

    public void setView(@NonNull GameView view) {
        this.gameView = view;
    }

    public void start(int levelNum) {
        List<Level> levels = gameRepository.getLevels();
        // FIXME
        Level level = null;
        for (Level item : levels) {
            if (item.getLevel() == levelNum) {
                level = item;
                break;
            }
        }
        if (level != null) {
            this.gameInitializeUseCase.execute(new GameInitializeObserver(), GameInitialize.Params.create(level.getWidth(), level.getHeight(), level.getMines()));
        }
    }

    public void open(int x, int y) {
        this.gameOpenUseCase.execute(new GameOpenObserver(), GameOpen.Params.create(x, y));
    }

    public void flag(int x, int y) {
        this.gameFlagUseCase.execute(new GameFlagObserver(), GameFlag.Params.create(x, y));
    }

    @Override public void resume() {}

    @Override public void pause() {}

    @Override public void destroy() {
        this.gameView = null;
    }

    private void update(GameBoard gameBoard) {
        gameView.updateGameBoard(gameBoard);
    }

    private final class GameInitializeObserver extends DefaultObserver<GameBoard> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(GameBoard gameBoard) {
            GamePresenter.this.update(gameBoard);
        }
    }

    private final class GameOpenObserver extends DefaultObserver<GameBoard> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(GameBoard gameBoard) {
            GamePresenter.this.update(gameBoard);
        }
    }

    private final class GameFlagObserver extends DefaultObserver<GameBoard> {

        @Override
        public void onComplete() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(GameBoard gameBoard) {
            GamePresenter.this.update(gameBoard);
        }
    }
}
