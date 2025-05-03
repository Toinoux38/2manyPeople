package com.citybuilder.model;

public class FireStation extends Building {
    private static final int FIRE_RADIUS = 5;
    private static final double FIRE_PREVENTION = 0.3;

    public FireStation(int x, int y) {
        super(x, y, "FIRE_STATION");
    }

    @Override
    public void update(World world) {
        // Réduire la probabilité d'incendie dans les bâtiments voisins
        for (int dx = -FIRE_RADIUS; dx <= FIRE_RADIUS; dx++) {
            for (int dy = -FIRE_RADIUS; dy <= FIRE_RADIUS; dy++) {
                int newX = x + dx;
                int newY = y + dy;
                
                if (newX >= 0 && newX < world.getWidth() && 
                    newY >= 0 && newY < world.getHeight()) {
                    Tile tile = world.getTile(newX, newY);
                    if (tile instanceof Building) {
                        Building building = (Building) tile;
                        building.setFireRisk(building.getFireRisk() * (1 - FIRE_PREVENTION));
                    }
                }
            }
        }
    }
} 