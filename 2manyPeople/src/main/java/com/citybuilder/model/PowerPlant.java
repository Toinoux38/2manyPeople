package com.citybuilder.model;

public class PowerPlant extends Zone {
    private static final int POWER_RADIUS = 5; // Rayon d'action de la centrale
    private static final int POWER_CAPACITY = 1000; // Capacité de production d'électricité
    private int powerOutput;

    public PowerPlant(int x, int y) {
        super(x, y, "POWER_PLANT", 2);
        this.powerOutput = POWER_CAPACITY;
    }

    @Override
    public void update(World world) {
        // Le PowerPlant ne nécessite pas de mise à jour
    }

    public int getPowerOutput() {
        return powerOutput;
    }

    public void setPowerOutput(int powerOutput) {
        this.powerOutput = powerOutput;
    }

    public int getPowerRadius() {
        return POWER_RADIUS;
    }

    public int getPowerCapacity() {
        return POWER_CAPACITY;
    }
} 