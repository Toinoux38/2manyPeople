package com.citybuilder.modelBis;

import java.util.List;

public class City {
    protected String name;
    protected Cell[][] map;
    protected List<Loan> loans;
    protected float hazardRate;
    protected int happiness;
    protected int electricityCapacity;
    protected int waterCapacity;
    protected int money;


    public City(String name, Cell[][] map, List<Loan> loans, float hazardRate, int money) {
        this.map = map;
        this.name = name;
        this.loans = loans;
        this.hazardRate = hazardRate;
        this.happiness = 50;
        this.money = money;
        electricityCapacity = 0;
        waterCapacity = 0;
    }

    public void toggleCell(int x, int y) {
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            map[x][y].isWater = !map[x][y].isWater; // Toggle water state
        }
    }

    public String getName() {
        return name;
    }
    public Cell[][] getMap() {
        return map;
    }
    public List<Loan> getLoans() {
        return loans;
    }
    public float getHazardRate() {
        return hazardRate;
    }
    public int getHappiness() {
        return happiness;
    }
    public int getElectricityCapacity() {
        return electricityCapacity;
    }
    public int getWaterCapacity() {
        return waterCapacity;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}
