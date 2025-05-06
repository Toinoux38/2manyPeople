package com.citybuilder.service;

import com.citybuilder.Sub;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.GameOverEvent;
import com.citybuilder.modelBis.events.StartEvent;
import com.citybuilder.modelBis.events.CityUpdateEvent;

import javax.inject.Inject;

public class GameService {
    private final GameStateService gameStateService;
    private final City city;

    @Inject
    public GameService(GameStateService gameStateService, City city) {
        this.gameStateService = gameStateService;
        this.city = city;

        // On prévient que le jeu commence
        gameStateService.submit(new StartEvent());

        // On s'abonne aux événements de fin de jeu
        gameStateService.subscribe(Sub.get(GameOverEvent.class, t -> stop()));
    }

    public void update() {
        city.update();
        gameStateService.submit(new CityUpdateEvent(city));
    }

    public void stop() {
        // Logique d'arrêt du jeu
    }

    public City getCity() {
        return city;
    }
}
