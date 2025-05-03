package com.citybuilder.controller;

import com.citybuilder.model.Tile;
import com.citybuilder.model.TileType;
import com.citybuilder.model.World;
import com.citybuilder.model.WorldEvent;
import com.citybuilder.model.WorldObserver;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final World world;
    private final List<WorldObserver> observers;
    private String selectedTool;

    public GameController(World world) {
        this.world = world;
        this.observers = new ArrayList<>();
        this.selectedTool = "ROAD";
    }

    public World getWorld() {
        return world;
    }

    public void addObserver(WorldObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WorldObserver observer) {
        observers.remove(observer);
    }

    public void placeTile(int x, int y, String toolType) {
        TileType type = TileType.valueOf(toolType);
        Tile tile = new Tile(x, y, type);
        world.setTile(x, y, tile);
        notifyObservers();
    }

    public void removeTile(int x, int y) {
        world.removeTile(x, y);
        notifyObservers();
    }

    public void setSelectedTool(String tool) {
        this.selectedTool = tool;
    }

    public String getSelectedTool() {
        return selectedTool;
    }

    private void notifyObservers() {
        for (WorldObserver observer : observers) {
            observer.update();
        }
    }

    public void triggerEvent(WorldEvent event) {
        world.addEvent(event);
        for (WorldObserver observer : observers) {
            observer.onWorldEvent(event);
        }
    }
} 