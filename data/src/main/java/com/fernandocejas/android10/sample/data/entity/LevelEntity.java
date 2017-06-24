package com.fernandocejas.android10.sample.data.entity;

public enum LevelEntity {
    LOW(1, 10, 10, 10), // 10%
    MIDDLE(2, 20, 20, 60), // 15%
    HIGH(3, 30, 30, 180); // 20%

    private int level;
    private int width;
    private int height;
    private int mines;

    LevelEntity(int level, int w, int h, int mines) {
        this.level = level;
        this.width = w;
        this.height = h;
        this.mines = mines;
    }

    public int getLevel() {
        return level;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }
}
