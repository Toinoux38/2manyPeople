package com.citybuilder.di;

import com.citybuilder.factory.DefaultGameStartupFactory;
import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameStartupDialog;

import dagger.Module;
import dagger.Provides;
import javafx.stage.Stage;

import javax.inject.Singleton;

@Module
public class GameModule {
    private final Stage stage;

    public GameModule(Stage stage) {
        this.stage = stage;
    }

    @Provides
    @Singleton
    Stage provideStage() {
        return stage;
    }

    @Provides
    @Singleton
    GameStartupFactory provideGameStartupFactory() {
        return new DefaultGameStartupFactory();
    }

    @Provides
    @Singleton
    GameStateService provideGameStateService() {
        return new GameStateService();
    }

    @Provides
    @Singleton
    GameStartupDialog provideGameStartupDialog(Stage stage, GameStartupFactory factory) {
        return new GameStartupDialog(stage, factory);
    }
} 