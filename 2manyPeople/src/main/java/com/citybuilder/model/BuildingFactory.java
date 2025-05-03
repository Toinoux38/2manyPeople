package com.citybuilder.model;

public class BuildingFactory {
    public static Building createBuilding(String type, int x, int y) {
        switch (type.toUpperCase()) {
            case "RESIDENTIAL":
                return new ResidentialZone(x, y);
            case "INDUSTRIAL":
                return new IndustrialZone(x, y);
            case "POWER_PLANT":
                return new PowerPlant(x, y);
            case "POWER_POLE":
                return new PowerPole(x, y);
            case "POLICE_STATION":
                return new PoliceStation(x, y);
            case "FIRE_STATION":
                return new FireStation(x, y);
            default:
                throw new IllegalArgumentException("Unknown building type: " + type);
        }
    }

    public static Event createEvent(String type, int x, int y) {
        switch (type.toUpperCase()) {
            case "FIRE":
                return new FireEvent(x, y);
            case "CRIME":
                return new CrimeEvent(x, y);
            default:
                throw new IllegalArgumentException("Unknown event type: " + type);
        }
    }
} 