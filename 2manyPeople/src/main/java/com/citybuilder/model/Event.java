package com.citybuilder.model;

public abstract class Event {
    protected final int x;
    protected final int y;
    protected final String name;
    protected boolean active;

    protected Event(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.active = true;
    }

    public abstract void update(World world);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }
} 