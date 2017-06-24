package com.fernandocejas.android10.sample.domain.interactor;

import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.executor.PostExecutionThread;
import com.fernandocejas.android10.sample.domain.executor.ThreadExecutor;
import com.fernandocejas.android10.sample.domain.repository.GameRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GameFlag extends UseCase<GameBoard, GameFlag.Params> {

    private final GameRepository gameRepository;

    @Inject
    GameFlag(GameRepository gameRepository, ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.gameRepository = gameRepository;
    }

    @Override
    Observable<GameBoard> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.gameRepository.flag(params.x, params.y);
    }

    public static final class Params {

        private final int x;
        private final int y;

        private Params(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Params create(int x, int y) {
            return new Params(x, y);
        }
    }
}
