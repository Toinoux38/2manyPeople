package com.citybuilder;

import com.citybuilder.model.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final World world;
    private AnimationTimer timer;
    private long lastUpdateTime;
    
    // Statistiques
    private final IntegerProperty totalPopulation;
    private final IntegerProperty totalWorkers;
    private final StringProperty satisfactionRate;

    public SimulationEngine(World world) {
        this.world = world;
        this.lastUpdateTime = System.nanoTime();
        this.totalPopulation = new SimpleIntegerProperty(0);
        this.totalWorkers = new SimpleIntegerProperty(0);
        this.satisfactionRate = new SimpleStringProperty("0%");
    }

    public void start() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= 1_000_000_000) { // Mise à jour chaque seconde
                    updateSimulation();
                    lastUpdateTime = now;
                }
            }
        };
        timer.start();
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void updateSimulation() {
        // Mettre à jour l'état de chaque bâtiment
        for (Building building : world.getBuildings()) {
            updateBuildingState(building);
            building.update();
        }
        
        // Mettre à jour les statistiques
        updateStatistics();
    }

    private void updateBuildingState(Building building) {
        // Vérifier l'accès aux routes
        building.setRoadAccess(checkRoadAccess(building));
        
        // Vérifier l'accès à l'électricité
        building.setPower(checkPowerAccess(building));
    }

    private boolean checkRoadAccess(Building building) {
        int x = building.getX();
        int y = building.getY();
        
        // Vérifier les tuiles adjacentes
        return isRoad(x-1, y) || isRoad(x+1, y) || isRoad(x, y-1) || isRoad(x, y+1);
    }

    private boolean checkPowerAccess(Building building) {
        int x = building.getX();
        int y = building.getY();
        
        // Vérifier si le bâtiment est dans le rayon d'une centrale ou d'un pylône
        for (Building powerSource : getPowerSources()) {
            int dx = Math.abs(x - powerSource.getX());
            int dy = Math.abs(y - powerSource.getY());
            int distance = dx + dy;
            
            int powerRadius = (powerSource instanceof PowerPlant) ? 
                ((PowerPlant) powerSource).getPowerRadius() : 
                ((PowerPole) powerSource).getPowerRadius();
                
            if (distance <= powerRadius) {
                return true;
            }
        }
        return false;
    }

    private List<Building> getPowerSources() {
        List<Building> powerSources = new ArrayList<>();
        for (Building building : world.getBuildings()) {
            if (building instanceof PowerPlant || building instanceof PowerPole) {
                powerSources.add(building);
            }
        }
        return powerSources;
    }

    private boolean isRoad(int x, int y) {
        Tile tile = world.getTile(x, y);
        return tile != null && tile.getType().equals("ROAD");
    }

    private void updateStatistics() {
        int population = 0;
        int workers = 0;
        int satisfiedBuildings = 0;
        int totalBuildings = 0;

        for (Building building : world.getBuildings()) {
            if (building instanceof ResidentialZone) {
                population += building.getPopulation();
                if (building.hasPower() && building.hasRoadAccess()) {
                    satisfiedBuildings++;
                }
                totalBuildings++;
            } else if (building instanceof IndustrialZone) {
                workers += ((IndustrialZone) building).getWorkers();
            }
        }

        totalPopulation.set(population);
        totalWorkers.set(workers);
        
        double satisfaction = totalBuildings > 0 ? 
            (double) satisfiedBuildings / totalBuildings * 100 : 0;
        satisfactionRate.set(String.format("%.0f%%", satisfaction));
    }

    // Getters pour les propriétés observables
    public IntegerProperty totalPopulationProperty() {
        return totalPopulation;
    }

    public IntegerProperty totalWorkersProperty() {
        return totalWorkers;
    }

    public StringProperty satisfactionRateProperty() {
        return satisfactionRate;
    }
} 