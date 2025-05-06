package com.citybuilder.service;


import com.citybuilder.Sub;
import com.citybuilder.modelBis.events.GameOverEvent;
import com.citybuilder.modelBis.events.StartEvent;

import javax.inject.Inject;

public class GameService {
    @Inject
    GameStateService gameStateService;

    public GameService() {

        // On prévient que le jeu commence
        gameStateService.submit(new StartEvent());

        gameStateService.subscribe(Sub.get(GameOverEvent.class, t -> stop()));
    }

    public void stop() {}
}
