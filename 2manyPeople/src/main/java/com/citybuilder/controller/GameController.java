package com.citybuilder.controller;

import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.CellType;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.CellPlacedEvent;
import com.citybuilder.modelBis.events.CellRemovedEvent;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.Sub;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class GameController implements Publisher<GameEvent> {
    private final City city;
    private final List<Subscriber<? super GameEvent>> subscribers;
    private String selectedTool;

    public GameController(City city) {
        this.city = city;
        this.subscribers = new ArrayList<>();
        this.selectedTool = "ROAD";
    }

    public City getCity() {
        return city;
    }

    @Override
    public void subscribe(Subscriber<? super GameEvent> subscriber) {
        subscribers.add(subscriber);
    }

    public void placeCell(int x, int y, String toolType) {
        Cell cell = city.getMap()[x][y];
        if (cell == null || !cell.canBeBuilt()) {
            return;
        }

        CellType type = CellType.valueOf(toolType);
        cell.setType(type);

        // Gérer les effets spécifiques au type de cellule
        switch (type) {
            case RESIDENTIAL:
                if (hasPowerNearby(x, y)) {
                    cell.setPopulation(10);
                }
                break;
            case INDUSTRIAL:
                if (hasPowerNearby(x, y)) {
                    cell.setWorkers(5);
                }
                break;
            case POWER_PLANT:
                updatePowerGrid();
                break;
            case POWER_POLE:
                updatePowerGrid();
                break;
        }

        notifySubscribers(new CellPlacedEvent(x, y));
    }

    public void removeCell(int x, int y) {
        Cell cell = city.getMap()[x][y];
        if (cell == null || !cell.canBeBulldozed()) {
            return;
        }

        // Sauvegarder le type avant de le réinitialiser
        CellType oldType = cell.getType();
        cell.setType(CellType.EMPTY);
        cell.setPower(false);
        cell.setPopulation(0);
        cell.setWorkers(0);

        // Si on supprime une centrale ou un pylône, mettre à jour le réseau électrique
        if (oldType == CellType.POWER_PLANT || oldType == CellType.POWER_POLE) {
            updatePowerGrid();
        }

        notifySubscribers(new CellRemovedEvent(x, y));
    }

    private boolean hasPowerNearby(int x, int y) {
        Cell[][] map = city.getMap();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length) {
                    Cell cell = map[nx][ny];
                    if (cell != null && cell.hasPower()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void updatePowerGrid() {
        Cell[][] map = city.getMap();
        boolean[][] hasPower = new boolean[map.length][map[0].length];
        
        // Marquer les centrales électriques
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] != null && map[x][y].getType() == CellType.POWER_PLANT) {
                    hasPower[x][y] = true;
                }
            }
        }

        // Propager l'électricité via les pylônes
        boolean changed;
        do {
            changed = false;
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map[0].length; y++) {
                    if (map[x][y] != null && map[x][y].getType() == CellType.POWER_POLE && !hasPower[x][y]) {
                        if (hasPowerNearby(x, y, hasPower)) {
                            hasPower[x][y] = true;
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);

        // Mettre à jour l'état de l'électricité pour toutes les cellules
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                if (map[x][y] != null) {
                    map[x][y].setPower(hasPower[x][y]);
                }
            }
        }
    }

    private boolean hasPowerNearby(int x, int y, boolean[][] hasPower) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < hasPower.length && ny >= 0 && ny < hasPower[0].length) {
                    if (hasPower[nx][ny]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setSelectedTool(String tool) {
        this.selectedTool = tool;
    }

    public String getSelectedTool() {
        return selectedTool;
    }

    private void notifySubscribers(GameEvent event) {
        for (Subscriber<? super GameEvent> subscriber : subscribers) {
            subscriber.onNext(event);
        }
    }

    public void triggerEvent(GameEvent event) {
        notifySubscribers(event);
    }

    public float getHazardRate() {
        return city.getHazardRate();
    }

    public String getCityName() {
        return city.getName();
    }

    public int getMoney() {
        return city.getMoney();
    }

    public int getTotalPopulation() {
        int total = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null) {
                    total += cell.getPopulation();
                }
            }
        }
        return total;
    }

    public int getTotalWorkers() {
        int total = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null) {
                    total += cell.getWorkers();
                }
            }
        }
        return total;
    }

    public int getAverageSatisfaction() {
        int total = 0;
        int count = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null && cell.getType() == CellType.RESIDENTIAL) {
                    total += cell.getSatisfaction();
                    count++;
                }
            }
        }
        return count > 0 ? total / count : 0;
    }
} 
} 