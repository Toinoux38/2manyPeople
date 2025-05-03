package com.citybuilder.model;

public class CrimeEvent extends Event {
    private static final double CRIME_RATE_INCREASE = 0.1;
    private static final int MAX_DURATION = 15;
    private int duration;

    public CrimeEvent(int x, int y) {
        super(x, y, "Crime");
        this.duration = MAX_DURATION;
    }

    @Override
    public void update(World world) {
        if (!active) return;

        // Réduire la durée
        duration--;
        if (duration <= 0) {
            active = false;
            return;
        }

        // Augmenter le taux de criminalité dans les zones résidentielles voisines
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                
                if (newX >= 0 && newX < world.getWidth() && 
                    newY >= 0 && newY < world.getHeight()) {
                    Tile tile = world.getTile(newX, newY);
                    if (tile instanceof ResidentialZone) {
                        ResidentialZone zone = (ResidentialZone) tile;
                        zone.increaseCrimeRate(CRIME_RATE_INCREASE);
                    }
                }
            }
        }
    }
} 