package com.citybuilder.model;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final int width;
    private final int height;
    private final Tile[][] grid;
    private final List<Building> buildings;
    private final List<Event> activeEvents;
    private final List<WorldObserver> observers;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];
        this.buildings = new ArrayList<>();
        this.activeEvents = new ArrayList<>();
        this.observers = new ArrayList<>();
        
        // Initialize grid with empty tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new EmptyTile(x, y);
            }
        }
    }

    public void addObserver(WorldObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WorldObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(WorldEvent event) {
        for (WorldObserver observer : observers) {
            observer.onWorldEvent(event);
        }
    }

    public boolean placeTile(int x, int y, Tile tile) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        if (tile instanceof Zone) {
            return placeZone(x, y, (Zone) tile);
        }

        if (grid[x][y].getType().equals("EMPTY")) {
            grid[x][y] = tile;
            if (tile instanceof Building) {
                buildings.add((Building) tile);
            }
            notifyObservers(new WorldEvent(WorldEventType.TILE_PLACED, tile));
            return true;
        }
        return false;
    }

    public boolean placeZone(int x, int y, Zone zone) {
        if (!canPlaceZone(x, y, zone.getSize())) {
            return false;
        }

        for (int i = 0; i < zone.getSize(); i++) {
            for (int j = 0; j < zone.getSize(); j++) {
                grid[x + i][y + j] = zone;
            }
        }
        buildings.add(zone);
        notifyObservers(new WorldEvent(WorldEventType.ZONE_PLACED, zone));
        return true;
    }

    private boolean canPlaceZone(int x, int y, int size) {
        if (x + size > width || y + size > height) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!grid[x + i][y + j].getType().equals("EMPTY")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void removeTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        Tile tile = grid[x][y];
        if (tile instanceof Zone) {
            removeZone(x, y, (Zone) tile);
        } else {
            if (tile instanceof Building) {
                buildings.remove(tile);
            }
            grid[x][y] = new EmptyTile(x, y);
            notifyObservers(new WorldEvent(WorldEventType.TILE_REMOVED, tile));
        }
    }

    private void removeZone(int x, int y, Zone zone) {
        int size = zone.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[x + i][y + j] = new EmptyTile(x + i, y + j);
            }
        }
        buildings.remove(zone);
        notifyObservers(new WorldEvent(WorldEventType.ZONE_REMOVED, zone));
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Building> getBuildings() {
        return new ArrayList<>(buildings);
    }

    public List<Event> getActiveEvents() {
        return new ArrayList<>(activeEvents);
    }

    public void addEvent(Event event) {
        activeEvents.add(event);
        notifyObservers(new WorldEvent(WorldEventType.EVENT_STARTED, event));
    }

    public void removeEvent(Event event) {
        activeEvents.remove(event);
        notifyObservers(new WorldEvent(WorldEventType.EVENT_ENDED, event));
    }
} 
