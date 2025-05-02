package com.citybuilder;

import com.citybuilder.model.World;
import com.citybuilder.model.Building;
import com.citybuilder.model.Tile;
import javafx.animation.AnimationTimer;

public class SimulationEngine {
    private final World world;
    private AnimationTimer timer;
    private long lastUpdateTime;

    public SimulationEngine(World world) {
        this.world = world;
        this.lastUpdateTime = System.nanoTime();
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
        // TODO: Implémenter la vérification de l'accès à l'électricité
        // Pour l'instant, retourne toujours true
        return true;
    }

    private boolean isRoad(int x, int y) {
        Tile tile = world.getTile(x, y);
        return tile != null && tile.getType().equals("ROAD");
    }
} 