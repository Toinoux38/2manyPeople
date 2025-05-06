package com.citybuilder.modelBis.events;

public abstract class GameEvent {
    private final String type;
    private final int x;
    private final int y;

    protected GameEvent(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
