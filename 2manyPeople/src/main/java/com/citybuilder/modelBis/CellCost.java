package com.citybuilder.modelBis;

public class CellCost {
    // Coûts de construction
    private static final int ROAD_COST = 10;
    private static final int RESIDENTIAL_COST = 100;
    private static final int INDUSTRIAL_COST = 150;
    private static final int POWER_PLANT_COST = 500;
    private static final int POWER_POLE_COST = 50;
    private static final int POLICE_STATION_COST = 300;
    private static final int FIRE_STATION_COST = 300;

    // Revenus mensuels
    private static final int RESIDENTIAL_INCOME = 5;
    private static final int INDUSTRIAL_INCOME = 10;
    private static final int POWER_PLANT_INCOME = 20;
    private static final int POLICE_STATION_INCOME = 15;
    private static final int FIRE_STATION_INCOME = 15;

    // Coûts mensuels
    private static final int RESIDENTIAL_MAINTENANCE = 2;
    private static final int INDUSTRIAL_MAINTENANCE = 5;
    private static final int POWER_PLANT_MAINTENANCE = 10;
    private static final int POWER_POLE_MAINTENANCE = 1;
    private static final int POLICE_STATION_MAINTENANCE = 8;
    private static final int FIRE_STATION_MAINTENANCE = 8;

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

    public static int getMonthlyIncome(CellType type) {
        switch (type) {
            case RESIDENTIAL:
                return RESIDENTIAL_INCOME;
            case INDUSTRIAL:
                return INDUSTRIAL_INCOME;
            case POWER_PLANT:
                return POWER_PLANT_INCOME;
            case POLICE_STATION:
                return POLICE_STATION_INCOME;
            case FIRE_STATION:
                return FIRE_STATION_INCOME;
            default:
                return 0;
        }
    }

    public static int getMonthlyMaintenance(CellType type) {
        switch (type) {
            case RESIDENTIAL:
                return RESIDENTIAL_MAINTENANCE;
            case INDUSTRIAL:
                return INDUSTRIAL_MAINTENANCE;
            case POWER_PLANT:
                return POWER_PLANT_MAINTENANCE;
            case POWER_POLE:
                return POWER_POLE_MAINTENANCE;
            case POLICE_STATION:
                return POLICE_STATION_MAINTENANCE;
            case FIRE_STATION:
                return FIRE_STATION_MAINTENANCE;
            default:
                return 0;
        }
    }
} 