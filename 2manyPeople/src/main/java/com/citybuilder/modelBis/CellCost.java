package com.citybuilder.modelBis;

public class CellCost {
    private static final int ROAD_COST = 10;
    private static final int RESIDENTIAL_COST = 100;
    private static final int INDUSTRIAL_COST = 150;
    private static final int POWER_PLANT_COST = 500;
    private static final int POWER_POLE_COST = 50;
    private static final int POLICE_STATION_COST = 300;
    private static final int FIRE_STATION_COST = 300;

    public static int getCost(CellType type) {
        switch (type) {
            case ROAD:
                return ROAD_COST;
            case RESIDENTIAL:
                return RESIDENTIAL_COST;
            case INDUSTRIAL:
                return INDUSTRIAL_COST;
            case POWER_PLANT:
                return POWER_PLANT_COST;
            case POWER_POLE:
                return POWER_POLE_COST;
            case POLICE_STATION:
                return POLICE_STATION_COST;
            case FIRE_STATION:
                return FIRE_STATION_COST;
            default:
                return 0;
        }
    }

    public static int getRefund(CellType type) {
        return getCost(type) / 4; // 25% du coût initial
    }
} 