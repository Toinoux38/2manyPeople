package com.citybuilder.modelBis.events;

public class CellPlacedEvent extends GameEvent {
    public CellPlacedEvent(int x, int y) {
        super("CELL_PLACED", x, y);
    }
} 