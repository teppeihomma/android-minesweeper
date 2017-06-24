package com.fernandocejas.android10.sample.data.entity;

public class GameBoardEntity {

    private int width;
    private int height;
    private int[][] field;
    private int[][] fieldStatuses;
    private GameStatusEntity status;
    private int mines;

    public GameBoardEntity(int width, int height, int[][] field, int[][] fieldStatuses,
                           GameStatusEntity status, int mines) {
        this.width = width;
        this.height = height;
        this.field = field;
        this.fieldStatuses = fieldStatuses;
        this.status = status;
        this.mines = mines;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getField() {
        return field;
    }

    public int[][] getFieldStatuses() {
        return fieldStatuses;
    }

    public GameStatusEntity getStatus() {
        return status;
    }

    public int getMines() {
        return mines;
    }
}
