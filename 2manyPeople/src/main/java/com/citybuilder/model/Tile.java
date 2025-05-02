package com.citybuilder.model;

public abstract class Tile {
    protected final int x;
    protected final int y;
    protected String type;

    public Tile(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public abstract void update();
} 