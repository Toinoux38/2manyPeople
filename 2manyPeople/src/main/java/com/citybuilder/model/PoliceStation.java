package com.citybuilder.model;

public class PoliceStation extends Building {
    private static final int POLICE_RADIUS = 5;
    private static final double CRIME_REDUCTION = 0.2;

    public PoliceStation(int x, int y) {
        super(x, y, "POLICE_STATION");
    }

    @Override
    public void update(World world) {
        // Réduire le taux de criminalité dans les zones résidentielles voisines
        for (int dx = -POLICE_RADIUS; dx <= POLICE_RADIUS; dx++) {
            for (int dy = -POLICE_RADIUS; dy <= POLICE_RADIUS; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                
                if (newX >= 0 && newX < world.getWidth() && 
                    newY >= 0 && newY < world.getHeight()) {
                    Tile tile = world.getTile(newX, newY);
                    if (tile instanceof ResidentialZone) {
                        ResidentialZone zone = (ResidentialZone) tile;
                        zone.decreaseCrimeRate(CRIME_REDUCTION);
                    }
                }
            }
        }
    }
} 