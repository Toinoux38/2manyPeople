package com.citybuilder.module;

import com.citybuilder.factory.CityConfigFactory;
import com.citybuilder.modelBis.City;
import com.citybuilder.service.GameService;
import com.citybuilder.service.GameStateService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class GameModule {

    @Singleton
    @Provides
    public GameStateService ProvideGameStateService() {
        return new GameStateService();
    }

    @Singleton
    @Provides
    public City ProvideCity() {
        return CityConfigFactory.createCityFromConfig();
    }

    @Singleton
    @Provides
    public GameService ProvideGameService(GameStateService gameStateService, City city) {
        return new GameService(gameStateService, city);
    }
}
