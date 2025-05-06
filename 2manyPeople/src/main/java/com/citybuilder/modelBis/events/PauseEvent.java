package com.citybuilder.modelBis.events;

public class PauseEvent extends GameEvent {
    protected PauseEvent(String type, int x, int y) {
        super(type, x, y);
    }
}
