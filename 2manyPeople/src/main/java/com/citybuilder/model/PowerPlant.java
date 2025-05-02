package com.citybuilder.model;

public class PowerPlant extends Building {
    private static final int POWER_RADIUS = 5; // Rayon d'action de la centrale
    private static final int POWER_CAPACITY = 1000; // Capacité de production d'électricité

    public PowerPlant(int x, int y) {
        super(x, y, "POWER_PLANT");
    }

    public int getPowerRadius() {
        return POWER_RADIUS;
    }

    public int getPowerCapacity() {
        return POWER_CAPACITY;
    }

    @Override
    public void update() {
        // La centrale électrique n'a pas besoin de mise à jour
    }
} 