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
        if (tile instanceof Zone) {
            return placeZone(x, y, (Zone) tile);
        } else {
            return placeSingleTile(x, y, tile);
        }
    }

    private boolean placeZone(int x, int y, Zone zone) {
        // Vérifier si la zone peut être placée
        if (!canPlaceZone(x, y, zone.getZoneSize())) {
            return false;
        }

        // Placer la zone
        for (int i = 0; i < zone.getZoneSize(); i++) {
            for (int j = 0; j < zone.getZoneSize(); j++) {
                if (zone.isCellOccupied(i, j)) {
                    grid[x + i][y + j] = zone;
                }
            }
        }

        buildings.add(zone);
        return true;
    }

    private boolean placeSingleTile(int x, int y, Tile tile) {
        if (isValidPosition(x, y)) {
            grid[x][y] = tile;
            if (tile instanceof Building) {
                buildings.add((Building) tile);
            }
            return true;
        }
        return false;
    }

    private boolean canPlaceZone(int x, int y, int size) {
        // Vérifier si la zone est dans les limites
        if (x < 0 || y < 0 || x + size > width || y + size > height) {
            return false;
        }

        // Vérifier si toutes les cellules sont vides
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!(grid[x + i][y + j] instanceof EmptyTile)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean removeTile(int x, int y) {
        if (isValidPosition(x, y)) {
            Tile tile = grid[x][y];
            if (tile instanceof Building) {
                buildings.remove(tile);
                if (tile instanceof Zone) {
                    removeZone(x, y, (Zone) tile);
                } else {
                    grid[x][y] = new EmptyTile(x, y);
                }
            }
            return true;
        }
        return false;
    }

    private void removeZone(int x, int y, Zone zone) {
        for (int i = 0; i < zone.getZoneSize(); i++) {
            for (int j = 0; j < zone.getZoneSize(); j++) {
                if (zone.isCellOccupied(i, j)) {
                    grid[x + i][y + j] = new EmptyTile(x + i, y + j);
                }
            }
        }
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