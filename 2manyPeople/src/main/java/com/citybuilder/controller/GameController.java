package com.citybuilder.controller;

import com.citybuilder.modelBis.Cell;
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
        Cell cell = new Cell(false, new java.awt.Point(x, y));
        city.getMap()[x][y] = cell;
        notifySubscribers(new CellPlacedEvent(x, y));
    }

    public void removeCell(int x, int y) {
        city.getMap()[x][y] = null;
        notifySubscribers(new CellRemovedEvent(x, y));
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
} 
} 