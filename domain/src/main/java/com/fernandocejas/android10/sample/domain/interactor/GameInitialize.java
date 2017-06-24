package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.GameRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GameInitialize extends UseCase<GameBoard, GameInitialize.Params> {

    private final GameRepository gameRepository;

    @Inject
    GameInitialize(GameRepository gameRepository, ThreadExecutor threadExecutor,
                   PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gameRepository = gameRepository;
    }

    @Override
    Observable<GameBoard> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.gameRepository.initialize(params.w, params.h, params.mines);
    }

    public static final class Params {

        private final int w;
        private final int h;
        private final int mines;

        private Params(int w, int h, int mines) {
            this.w = w;
            this.h = h;
            this.mines = mines;
        }

        public static Params create(int w, int h, int mines) {
            return new Params(w, h, mines);
        }
    }
}
