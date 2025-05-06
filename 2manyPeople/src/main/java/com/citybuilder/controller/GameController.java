package com.citybuilder.controller;

import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.service.GameService;
import com.citybuilder.Sub;

import javax.inject.Inject;

public class GameController {
    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public void update() {
        gameService.update();
    }

    public void subscribeToEvents(Class<? extends GameEvent> eventType, java.util.function.Consumer<? extends GameEvent> consumer) {
        gameService.getGameStateService().subscribe(Sub.get(eventType, consumer));
    }

    public City getCity() {
        return gameService.getCity();
    }
} 