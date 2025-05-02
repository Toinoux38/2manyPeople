package com.citybuilder.model;

public class IndustrialZone extends Zone {
    private static final int MAX_WORKERS = 50;
    private int workers;

    public IndustrialZone(int x, int y) {
        super(x, y, "INDUSTRIAL");
        this.workers = 0;
    }

    public int getWorkers() {
        return workers;
    }

    @Override
    public void update() {
        if (hasPower && hasRoadAccess) {
            // Le nombre de travailleurs augmente progressivement jusqu'au maximum
            workers = Math.min(workers + 1, MAX_WORKERS);
        } else {
            // Le nombre de travailleurs diminue si les conditions ne sont pas remplies
            workers = Math.max(workers - 1, 0);
        }
    }
} 