package com.citybuilder.model;

public abstract class Building extends Tile {
    protected int population;
    protected boolean hasPower;
    protected boolean hasRoadAccess;

    public Building(int x, int y, String type) {
        super(x, y, type);
        this.population = 0;
        this.hasPower = false;
        this.hasRoadAccess = false;
    }

    public int getPopulation() {
        return population;
    }

    public boolean hasPower() {
        return hasPower;
    }

    public boolean hasRoadAccess() {
        return hasRoadAccess;
    }

    public void setPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public void setRoadAccess(boolean hasRoadAccess) {
        this.hasRoadAccess = hasRoadAccess;
    }

    @Override
    public abstract void update();
} 