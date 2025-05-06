package com.citybuilder.modelBis;

import java.awt.Point;

public class Cell {
    protected Boolean isWater;
    protected Point location;
    protected CellType type;
    protected boolean hasPower;
    protected int population;
    protected int workers;
    protected int satisfaction;

    public Cell(Boolean isWater, Point location) {
        this.isWater = isWater;
        this.location = location;
        this.type = CellType.EMPTY;
        this.hasPower = false;
        this.population = 0;
        this.workers = 0;
        this.satisfaction = 0;
    }

    public Boolean getIsWater() {
        return isWater;
    }

    public Point getLocation() {
        return location;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public boolean hasPower() {
        return hasPower;
    }

    public void setPower(boolean hasPower) {
        this.hasPower = hasPower;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getWorkers() {
        return workers;
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public boolean canBeBuilt() {
        return !isWater && type == CellType.EMPTY;
    }

    public boolean canBeBulldozed() {
        return !isWater && type != CellType.EMPTY;
    }
}
