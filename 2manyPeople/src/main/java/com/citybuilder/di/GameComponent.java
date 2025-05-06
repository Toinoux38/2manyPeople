package com.citybuilder.di;

import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameStartupDialog;

import dagger.Component;
import javafx.stage.Stage;

import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameComponent {
    Stage primaryStage();
    GameStartupFactory gameStartupFactory();
    GameStateService gameStateService();
    GameStartupDialog gameStartupDialog();
} 