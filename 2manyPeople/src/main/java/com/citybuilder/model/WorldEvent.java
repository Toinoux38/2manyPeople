package com.citybuilder.model;

public class WorldEvent {
    private final WorldEventType type;
    private final Object source;

    public WorldEvent(WorldEventType type, Object source) {
        this.type = type;
        this.source = source;
    }

    public WorldEventType getType() {
        return type;
    }

    public Object getSource() {
        return source;
    }
} 