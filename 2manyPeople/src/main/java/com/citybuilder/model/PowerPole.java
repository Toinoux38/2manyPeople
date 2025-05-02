package com.citybuilder.model;

public class PowerPole extends Building {
    private static final int POWER_RADIUS = 2; // Rayon d'action du pylône

    public PowerPole(int x, int y) {
        super(x, y, "POWER_POLE");
    }

    public int getPowerRadius() {
        return POWER_RADIUS;
    }

    @Override
    public void update() {
        // Le pylône n'a pas besoin de mise à jour
    }
} 