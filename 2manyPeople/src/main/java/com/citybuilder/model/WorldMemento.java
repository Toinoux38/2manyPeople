package com.citybuilder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorldMemento implements Serializable {
    private final int width;
    private final int height;
    private final List<Tile> tiles;
    private final List<Building> buildings;
    private final List<Event> activeEvents;

    public WorldMemento(World world) {
        this.width = world.getWidth();
        this.height = world.getHeight();
        this.tiles = new ArrayList<>();
        this.buildings = new ArrayList<>(world.getBuildings());
        this.activeEvents = new ArrayList<>(world.getActiveEvents());

        // Sauvegarder toutes les tuiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles.add(world.getTile(x, y));
            }
        }
    }

    public World restore() {
        World world = new World(width, height);
        
        // Restaurer les tuiles
        int index = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world.placeTile(x, y, tiles.get(index++));
            }
        }

        // Restaurer les événements actifs
        for (Event event : activeEvents) {
            world.addEvent(event);
        }

        return world;
    }
} 