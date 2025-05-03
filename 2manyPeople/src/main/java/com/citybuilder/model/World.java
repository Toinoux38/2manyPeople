package com.citybuilder.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class World {
    private final int width;
    private final int height;
    private final Tile[][] grid;
    private final IntegerProperty totalPopulation;
    private final IntegerProperty totalWorkers;
    private final IntegerProperty satisfactionRate;
    private final ObservableList<WorldEvent> events;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Tile[width][height];
        this.totalPopulation = new SimpleIntegerProperty(0);
        this.totalWorkers = new SimpleIntegerProperty(0);
        this.satisfactionRate = new SimpleIntegerProperty(50);
        this.events = FXCollections.observableArrayList();

        // Initialiser la grille avec des cases vides
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Tile(x, y, TileType.EMPTY);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return null;
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[x][y] = tile;
            updateStats();
        }
    }

    public void removeTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[x][y] = new Tile(x, y, TileType.EMPTY);
            updateStats();
        }
    }

    private void updateStats() {
        // Mettre à jour les statistiques en fonction des bâtiments présents
        int population = 0;
        int workers = 0;
        int satisfaction = 0;
        int residentialCount = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = grid[x][y];
                if (tile.getType() == TileType.RESIDENTIAL) {
                    population += 100; // Exemple : chaque zone résidentielle = 100 habitants
                    residentialCount++;
                } else if (tile.getType() == TileType.INDUSTRIAL) {
                    workers += 50; // Exemple : chaque zone industrielle = 50 travailleurs
                }
            }
        }

        // Calculer la satisfaction moyenne
        if (residentialCount > 0) {
            satisfaction = 50; // Base satisfaction
            // Ajouter des bonus/malus selon les services disponibles
        }

        totalPopulation.set(population);
        totalWorkers.set(workers);
        satisfactionRate.set(satisfaction);
    }

    public IntegerProperty totalPopulationProperty() {
        return totalPopulation;
    }

    public IntegerProperty totalWorkersProperty() {
        return totalWorkers;
    }

    public IntegerProperty satisfactionRateProperty() {
        return satisfactionRate;
    }

    public ObservableList<WorldEvent> getEvents() {
        return events;
    }

    public void addEvent(WorldEvent event) {
        events.add(event);
    }
} 