package com.citybuilder.model;

public class ResidentialZone extends Zone {
    private static final int MAX_POPULATION = 100;

    public ResidentialZone(int x, int y) {
        super(x, y, "RESIDENTIAL");
    }

    @Override
    public void update() {
        if (hasPower && hasRoadAccess) {
            // La population augmente progressivement jusqu'au maximum
            population = Math.min(population + 1, MAX_POPULATION);
        } else {
            // La population diminue si les conditions ne sont pas remplies
            population = Math.max(population - 1, 0);
        }
    }
} 