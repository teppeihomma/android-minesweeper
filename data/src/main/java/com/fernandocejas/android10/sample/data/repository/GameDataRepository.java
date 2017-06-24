package com.fernandocejas.android10.sample.data.repository;

import android.support.annotation.NonNull;

import com.fernandocejas.android10.sample.data.cache.MemoryCache;
import com.fernandocejas.android10.sample.data.entity.GameBoardEntity;
import com.fernandocejas.android10.sample.data.entity.GameStatusEntity;
import com.fernandocejas.android10.sample.data.entity.LevelEntity;
import com.fernandocejas.android10.sample.data.entity.mapper.GameBoardEntityDataMapper;
import com.fernandocejas.android10.sample.data.entity.mapper.LevelEntityDataMapper;
import com.fernandocejas.android10.sample.domain.GameBoard;
import com.fernandocejas.android10.sample.domain.GameStatus;
import com.fernandocejas.android10.sample.domain.Level;
import com.fernandocejas.android10.sample.domain.repository.GameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

@Singleton
public class GameDataRepository implements GameRepository {

    private final MemoryCache memoryCache;
    private final GameBoardEntityDataMapper gameBoardEntityDataMapper;
    private final LevelEntityDataMapper levelEntityDataMapper;

    @Inject
    public GameDataRepository(
            MemoryCache memoryCache,
            GameBoardEntityDataMapper gameBoardEntityDataMapper,
            LevelEntityDataMapper levelEntityDataMapper) {
        this.memoryCache = memoryCache;
        this.gameBoardEntityDataMapper = gameBoardEntityDataMapper;
        this.levelEntityDataMapper = levelEntityDataMapper;
    }

    @Override
    public Observable<GameBoard> initialize(int w, int h, int mines) {
        return Observable.create(emitter -> {
            // フィールド初期化
            int[][] field = new int[w][h];
            int[][] fieldStatuses = new int[w][h];
            initializeField(w, h, field, mines);

            // TODO マス目数＜＝地雷数の場合はエラー

            GameBoardEntity gameBoardEntity = new GameBoardEntity(w, h, field, fieldStatuses,
                    GameStatusEntity.INITIALIZED, mines);

            memoryCache.putGameBoard(gameBoardEntity);

            emitter.onNext(gameBoardEntityDataMapper.transform(gameBoardEntity));

            emitter.onComplete();
        });
    }

    @Override
    public Observable<GameBoard> open(int x, int y) {
        return Observable.create(emitter -> {
            GameBoardEntity gameBoardEntity = memoryCache.getGameBoard();
            GameBoard gameBoard = gameBoardEntityDataMapper.transform(gameBoardEntity);
            int[][] field = gameBoard.getField();

            // 再帰オープン
            openRecursive(gameBoard, x, y, emitter);

            // 判定
            if (field[x][y] == -1) {
                gameBoard.setStatus(GameStatus.GAME_OVER);
            }
            else if (isCleared(gameBoard)) {
                gameBoard.setStatus(GameStatus.CLEARED);
            }
            emitter.onComplete();
        });
    }

    @Override
    public Observable<GameBoard> flag(int x, int y) {
        return Observable.create(emitter -> {
            GameBoardEntity gameBoardEntity = memoryCache.getGameBoard();
            GameBoard gameBoard = gameBoardEntityDataMapper.transform(gameBoardEntity);
            int[][] fieldStatuses = gameBoard.getFieldStatuses();

            if (fieldStatuses[x][y] == GameBoard.FieldStatus.FLAG) {
                fieldStatuses[x][y] = GameBoard.FieldStatus.CLOSE;
            }
            else if (fieldStatuses[x][y] == GameBoard.FieldStatus.CLOSE) {
                fieldStatuses[x][y] = GameBoard.FieldStatus.FLAG;
            }
            emitter.onNext(gameBoard);
            emitter.onComplete();
        });
    }

    @Override
    public List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        for (LevelEntity entity : LevelEntity.values()) {
            levels.add(levelEntityDataMapper.transform(entity));
        }
        return levels;
    }

    private void initializeField(int w, int h, int[][] field, int mines) {
        // 連番リスト作成
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < w * h; i++) {
            numbers.add(i);
        }

        // リストシャッフル
        Collections.shuffle(numbers);

        // 先頭から地雷数分だけ取得して地雷配置
        for (int i = 0; i < mines; i++) {
            int pos = numbers.get(i);
            int x = pos / w;
            int y = pos % h;
            field[x][y] = -1; // 地雷を-1で表す
        }

        // 地雷数のカウント作成
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                // 中心点が地雷の場合はスキップ
                if (field[x][y] == -1) {
                    continue;
                }

                int count = 0;
                // 中心点の周囲８点を調べる
                for (int x1 = -1; x1 <= 1; x1++) {
                    for (int y1 = -1; y1 <= 1; y1++) {
                        // フィールド範囲外の判定
                        if (x + x1 >= 0 && x + x1 < w && y + y1 >= 0 && y + y1 < h) {
                            // 中心点は除外
                            if (!(x1 == 0 && y1 == 0)) {
                                // 地雷があれば1を加算
                                count += (field[x + x1][y + y1] == -1) ? 1 : 0;
                            }
                        }
                    }
                }
                // フィールドにカウント数を設定
                field[x][y] = count;
            }
        }
    }

    private boolean isCleared(@NonNull GameBoard gameBoard) {
        if (gameBoard.getStatus() == GameStatus.GAME_OVER) {
            return false;
        }
        int[][] fieldStatuses = gameBoard.getFieldStatuses();
        boolean isCleared = false;
        int openedCount = 0;
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                openedCount += (fieldStatuses[x][y] == GameBoard.FieldStatus.OPENED) ? 1 : 0;
            }
        }
        return (gameBoard.getWidth() * gameBoard.getHeight()) - openedCount - gameBoard.getMines() == 0;
    }

    private void openRecursive(
            @NonNull GameBoard gameBoard, int x, int y, ObservableEmitter<GameBoard> emitter) {
        int[][] field = gameBoard.getField();
        int[][] fieldStatuses = gameBoard.getFieldStatuses();
        int w = gameBoard.getWidth();
        int h = gameBoard.getHeight();

        // 閉じているか、フラグが立っている場所はオープンできる
        if (fieldStatuses[x][y] == GameBoard.FieldStatus.CLOSE ||
                fieldStatuses[x][y] == GameBoard.FieldStatus.FLAG) {
            fieldStatuses[x][y] = GameBoard.FieldStatus.OPENED;
        }

        // 中心が0である場合は周囲を検索する
        if (field[x][y] == 0) {
            // 中心点の周囲８点を調べる
            for (int x1 = -1; x1 <= 1; x1++) {
                for (int y1 = -1; y1 <= 1; y1++) {
                    // フィールド範囲外の判定
                    if (x + x1 >= 0 && x + x1 < w && y + y1 >= 0 && y + y1 < h) {
                        // 中心点は除外
                        if (!(x1 == 0 && y1 == 0)) {
                            if (field[x + x1][y + y1] != -1 && fieldStatuses[x + x1][y + y1] != GameBoard.FieldStatus.OPENED) {
                                // 地雷でなければ再帰検索する
                                openRecursive(gameBoard, x + x1, y + y1, emitter);
                            }
                        }
                    }
                }
            }
        }

        emitter.onNext(gameBoard);
    }
}
