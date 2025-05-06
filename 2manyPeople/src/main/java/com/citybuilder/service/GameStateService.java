package com.citybuilder.service;

import com.citybuilder.Sub;
import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.CellCost;
import com.citybuilder.modelBis.CellType;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.modelBis.events.PauseEvent;

import java.util.concurrent.SubmissionPublisher;

public class GameStateService extends SubmissionPublisher<GameEvent> {
    private City city;
    private boolean paused = false;

    public GameStateService() {
        this.subscribe(Sub.get(PauseEvent.class, t -> pause()));
    }

    public boolean purchase(CellType type, Cell cell) {
        if (this.getCity().getMoney() < CellCost.getCost(type) || !cell.canBeBuilt()) {
            return false;
        }
        this.getCity().setMoney(this.getCity().getMoney() - CellCost.getCost(type));
        cell.setType(type);
        return true;
    }

    public boolean destroy(Cell cell) {
        if (cell.canBeBulldozed()) {
            CellType oldType = cell.getType();
            this.getCity().setMoney(this.getCity().getMoney() + CellCost.getRefund(oldType));
            cell.setType(CellType.EMPTY);
            cell.setPower(false);
            cell.setPopulation(0);
            cell.setWorkers(0);
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

    public void setCity(City city) {
        this.city = city;
    }
}
