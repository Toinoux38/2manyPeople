package com.citybuilder.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;

public class SimulationEngine {
    private final World world;
    private final IntegerProperty totalPopulation;
    private final IntegerProperty totalWorkers;
    private final DoubleProperty satisfactionRate;
    private double timeSinceLastEvent;
    private static final double EVENT_INTERVAL = 30.0; // Secondes entre les événements

    public SimulationEngine(World world) {
        this.world = world;
        this.totalPopulation = new SimpleIntegerProperty(0);
        this.totalWorkers = new SimpleIntegerProperty(0);
        this.satisfactionRate = new SimpleDoubleProperty(0.0);
        this.timeSinceLastEvent = 0.0;
    }

    public void update() {
        updateBuildings();
        updateEvents();
        updateStatistics();
    }

    private void updateBuildings() {
        for (Building building : world.getBuildings()) {
            building.update(world);
        }
    }

    private void updateEvents() {
        timeSinceLastEvent += 1.0;
        
        if (timeSinceLastEvent >= EVENT_INTERVAL) {
            timeSinceLastEvent = 0.0;
            triggerRandomEvent();
        }
    }

    private void triggerRandomEvent() {
        double random = Math.random();
        
        if (random < 0.3) { // 30% de chance d'incendie
            triggerFireEvent();
        } else if (random < 0.6) { // 30% de chance de criminalité
            triggerCrimeEvent();
        } else if (random < 0.8) { // 20% de chance de coupure de courant
            triggerPowerOutage();
        }
    }

    private void triggerFireEvent() {
        // Trouver un bâtiment aléatoire
        if (!world.getBuildings().isEmpty()) {
            Building target = world.getBuildings().get((int)(Math.random() * world.getBuildings().size()));
            world.addEvent(new FireEvent(target.getX(), target.getY()));
        }
    }

    private void triggerCrimeEvent() {
        // Trouver une zone résidentielle aléatoire
        for (Building building : world.getBuildings()) {
            if (building instanceof ResidentialZone && Math.random() < 0.1) {
                world.addEvent(new CrimeEvent(building.getX(), building.getY()));
                break;
            }
        }
    }

    private void triggerPowerOutage() {
        // Simuler une coupure de courant
        for (Building building : world.getBuildings()) {
            if (building instanceof PowerPlant) {
                PowerPlant powerPlant = (PowerPlant) building;
                powerPlant.setPowerOutput(0);
                break;
            }
        }
    }

    private void updateStatistics() {
        int population = 0;
        int workers = 0;
        double satisfaction = 0.0;
        int residentialCount = 0;

        for (Building building : world.getBuildings()) {
            if (building instanceof ResidentialZone) {
                ResidentialZone zone = (ResidentialZone) building;
                population += zone.getPopulation();
                satisfaction += zone.getSatisfaction();
                residentialCount++;
            } else if (building instanceof IndustrialZone) {
                IndustrialZone zone = (IndustrialZone) building;
                workers += zone.getWorkers();
            }
        }

        totalPopulation.set(population);
        totalWorkers.set(workers);
        satisfactionRate.set(residentialCount > 0 ? satisfaction / residentialCount : 0.0);
    }

    public IntegerProperty totalPopulationProperty() {
        return totalPopulation;
    }

    public IntegerProperty totalWorkersProperty() {
        return totalWorkers;
    }

    public DoubleProperty satisfactionRateProperty() {
        return satisfactionRate;
    }
} 