package com.citybuilder.service;


import com.citybuilder.Sub;
import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.modelBis.events.PauseEvent;
import com.citybuilder.modelBis.purchasable.Purchasable;

import java.awt.*;
import java.util.concurrent.SubmissionPublisher;

public class GameStateService extends SubmissionPublisher<GameEvent> {
    private City city;
    private boolean paused = false;

    public GameStateService() {
        this.subscribe(Sub.get(PauseEvent.class, t -> pause()));
    }

    public boolean purchase(Purchasable content, Cell cell) {
        Point point = cell.getLocation();
        if (this.getCity().getMoney() < content.getPrice() || cell.getContent() != null || cell.getIsWater()) {
            return false;
        }
        this.getCity().setMoney(this.getCity().getMoney() - content.getPrice());
        cell.setContent(content);
        return true;
    }

    public boolean destroy(Cell cell) {
        if(cell.getContent() != null) {
            this.getCity().setMoney(this.getCity().getMoney() + (cell.getContent().getPrice() / 4));
            cell.setContent(null);
            return true;
        }
        return false;
    }

    protected void pause() {
        if (!paused) {
            paused = true;
            return;
        }
        paused = false;
    }

    public City getCity() {
        return city;
    }


}
