package com.citybuilder.modelBis.events;

public class GameOverEvent extends GameEvent {
    protected GameOverEvent(String type, int x, int y) {
        super(type, x, y);
    }
}
