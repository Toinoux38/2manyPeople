package com.citybuilder.di;

import com.citybuilder.factory.DefaultGameStartupFactory;
import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.service.GameStateService;

import dagger.Module;
import dagger.Provides;

@Module
public class GameModule {
    
    @Provides
    GameStartupFactory provideGameStartupFactory() {
        return new DefaultGameStartupFactory();
    }
    
    @Provides
    GameStateService provideGameStateService() {
        return new GameStateService();
    }
} 