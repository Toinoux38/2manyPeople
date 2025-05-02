package com.citybuilder.model;

public class Road extends Tile {
    public Road(int x, int y) {
        super(x, y, "ROAD");
    }

    @Override
    public void update() {
        // Les routes n'ont pas besoin de mise à jour
    }
} 