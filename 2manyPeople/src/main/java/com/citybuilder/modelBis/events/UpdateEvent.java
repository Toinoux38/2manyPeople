package com.citybuilder.modelBis.events;


import com.citybuilder.modelBis.Cell;

public class UpdateEvent extends GameEvent {
    public final Cell[][] map;

    public UpdateEvent(Cell[][] map) {
        this.map = map;
    }
}
