package com.citybuilder.module;

import com.citybuilder.controller.GameController;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {GameModule.class})
public interface GameComponent {
    GameController provideGameController();
} 