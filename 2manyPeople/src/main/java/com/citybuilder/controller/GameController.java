package com.citybuilder.controller;

import com.citybuilder.model.*;
import com.citybuilder.ui.GameView;
import javafx.animation.AnimationTimer;

import java.io.*;
import java.util.Stack;

public class GameController implements WorldObserver {
    private final World world;
    private final GameView view;
    private final SimulationEngine simulationEngine;
    private final Stack<WorldMemento> undoStack;
    private final Stack<WorldMemento> redoStack;
    private final AnimationTimer gameLoop;

    public GameController(World world, GameView view) {
        this.world = world;
        this.view = view;
        this.simulationEngine = new SimulationEngine(world);
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        
        // Enregistrer le contrôleur comme observateur du monde
        world.addObserver(this);
        
        // Initialiser la boucle de jeu
        this.gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) { // Mise à jour chaque seconde
                    update();
                    lastUpdate = now;
                }
            }
        };
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    private void update() {
        // Mettre à jour la simulation
        simulationEngine.update();
        
        // Mettre à jour les événements
        for (Event event : world.getActiveEvents()) {
            event.update(world);
            if (!event.isActive()) {
                world.removeEvent(event);
            }
        }
        
        // Mettre à jour la vue
        view.update();
    }

    public void placeTile(int x, int y, String type) {
        saveState();
        Building building = BuildingFactory.createBuilding(type, x, y);
        world.placeTile(x, y, building);
    }

    public void removeTile(int x, int y) {
        saveState();
        world.removeTile(x, y);
    }

    private void saveState() {
        undoStack.push(new WorldMemento(world));
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new WorldMemento(world));
            WorldMemento memento = undoStack.pop();
            World restoredWorld = memento.restore();
            restoreWorld(restoredWorld);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new WorldMemento(world));
            WorldMemento memento = redoStack.pop();
            World restoredWorld = memento.restore();
            restoreWorld(restoredWorld);
        }
    }

    private void restoreWorld(World restoredWorld) {
        // Copier l'état du monde restauré dans le monde actuel
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                world.removeTile(x, y);
                Tile tile = restoredWorld.getTile(x, y);
                if (!(tile instanceof EmptyTile)) {
                    world.placeTile(x, y, tile);
                }
            }
        }
    }

    public void saveGame(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(new WorldMemento(world));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            WorldMemento memento = (WorldMemento) in.readObject();
            World restoredWorld = memento.restore();
            restoreWorld(restoredWorld);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        switch (event.getType()) {
            case FIRE_STARTED:
                view.showNotification("Un incendie s'est déclaré !");
                break;
            case CRIME_STARTED:
                view.showNotification("La criminalité augmente dans la zone !");
                break;
            case POWER_OUTAGE_STARTED:
                view.showNotification("Coupure de courant !");
                break;
        }
    }

    public World getWorld() {
        return world;
    }

    public SimulationEngine getSimulationEngine() {
        return simulationEngine;
    }

    public GameView getView() {
        return view;
    }
} 