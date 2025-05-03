package com.citybuilder.model;

public class FireEvent extends Event {
    private static final double SPREAD_PROBABILITY = 0.1;
    private static final int MAX_DURATION = 10;
    private int duration;

    public FireEvent(int x, int y) {
        super(x, y, "Fire");
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

        // Propagation du feu
        if (Math.random() < SPREAD_PROBABILITY) {
            int newX = x + (int)(Math.random() * 3) - 1;
            int newY = y + (int)(Math.random() * 3) - 1;
            
            if (newX >= 0 && newX < world.getWidth() && 
                newY >= 0 && newY < world.getHeight()) {
                Tile tile = world.getTile(newX, newY);
                if (tile instanceof Building) {
                    world.addEvent(new FireEvent(newX, newY));
                }
            }
        }
    }
} 