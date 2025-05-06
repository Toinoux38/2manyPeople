package com.citybuilder.di;

import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameStartupDialog;
import javafx.stage.Stage;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameComponent {
    Stage getStage();
    GameStartupFactory getGameStartupFactory();
    GameStateService getGameStateService();
    GameStartupDialog getGameStartupDialog();
} 