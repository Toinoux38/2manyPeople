package com.citybuilder.model;

public class EmptyTile extends Tile {
    public EmptyTile(int x, int y) {
        super(x, y, "EMPTY");
    }

    @Override
    public void update() {
        // Rien à mettre à jour pour une tuile vide
    }
} 