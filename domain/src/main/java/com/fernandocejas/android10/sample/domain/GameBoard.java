package com.fernandocejas.android10.sample.domain;

public class GameBoard {

    public static class FieldStatus {
        public static final int CLOSE = 0;
        public static final int OPENED = 1;
        public static final int FLAG = 2;
    }

    private int width;
    private int height;
    private int[][] field;
    private int[][] fieldStatuses;
    private GameStatus status;

    private int mines;

    public GameBoard() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public int[][] getFieldStatuses() {
        return fieldStatuses;
    }

    public void setFieldStatuses(int[][] fieldStatuses) {
        this.fieldStatuses = fieldStatuses;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }
}
