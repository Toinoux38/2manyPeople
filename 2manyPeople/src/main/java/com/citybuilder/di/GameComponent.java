package com.citybuilder.di;

import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.service.GameStateService;

import dagger.Component;

@Component(modules = GameModule.class)
public interface GameComponent {
    GameStartupFactory gameStartupFactory();
    GameStateService gameStateService();
} 