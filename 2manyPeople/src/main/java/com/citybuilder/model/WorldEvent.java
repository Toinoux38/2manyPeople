package com.citybuilder.model;

public class WorldEvent {
    public enum EventType {
        FIRE_STARTED,
        CRIME_STARTED,
        POWER_OUTAGE,
        POPULATION_GROWTH,
        POPULATION_DECLINE
    }

    private final EventType type;
    private final String message;
    private final int x;
    private final int y;

    public WorldEvent(EventType type, String message, int x, int y) {
        this.type = type;
        this.message = message;
        this.x = x;
        this.y = y;
    }

    public EventType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
} 