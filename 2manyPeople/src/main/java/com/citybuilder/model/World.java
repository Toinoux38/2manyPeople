package com.citybuilder.model;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final int width;
    private final int height;
    private final Tile[][] grid;
    private final List<Building> buildings;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];
        this.buildings = new ArrayList<>();
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new EmptyTile(x, y);
            }
        }
    }

    public boolean placeTile(int x, int y, Tile tile) {
        if (isValidPosition(x, y)) {
            grid[x][y] = tile;
            if (tile instanceof Building) {
                buildings.add((Building) tile);
            }
            return true;
        }
        return false;
    }

    public boolean removeTile(int x, int y) {
        if (isValidPosition(x, y)) {
            Tile tile = grid[x][y];
            if (tile instanceof Building) {
                buildings.remove(tile);
            }
            grid[x][y] = new EmptyTile(x, y);
            return true;
        }
        return false;
    }

    public Tile getTile(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return null;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Building> getBuildings() {
        return buildings;
    }
} 