package com.citybuilder.modelBis.events;

public class CellRemovedEvent extends GameEvent {
    public CellRemovedEvent(int x, int y) {
        super("CELL_REMOVED", x, y);
    }
} 