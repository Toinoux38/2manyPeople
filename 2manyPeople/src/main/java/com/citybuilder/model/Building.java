package com.citybuilder.model;

public abstract class Building extends Tile {
    private double fireRisk;
    private boolean hasPower;
    private boolean hasRoadAccess;

    public Building(int x, int y, String type) {
        super(x, y, type);
        this.fireRisk = 0.0;
        this.hasPower = false;
        this.hasRoadAccess = false;
    }

    public abstract void update(World world);

    public double getFireRisk() {
        return fireRisk;
    }

    public void setFireRisk(double fireRisk) {
        this.fireRisk = fireRisk;
    }

    public boolean hasPower() {
        return hasPower;
    }

    public void setHasPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public boolean hasRoadAccess() {
        return hasRoadAccess;
    }

    public void setHasRoadAccess(boolean hasRoadAccess) {
        this.hasRoadAccess = hasRoadAccess;
    }

    @Override
    public abstract void update();
} 