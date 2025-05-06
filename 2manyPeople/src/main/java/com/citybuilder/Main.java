// 2ManyPeople - 2025 - youmix27 & toinoux38
package com.citybuilder;

import com.citybuilder.di.DaggerGameComponent;
import com.citybuilder.di.GameComponent;
import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.modelBis.City;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameComponent gameComponent;
    private GameStartupFactory gameStartupFactory;
    private GameStateService gameStateService;

    @Override
    public void start(Stage primaryStage) {
        // Initialiser Dagger
        gameComponent = DaggerGameComponent.create();
        gameStartupFactory = gameComponent.gameStartupFactory();
        gameStateService = gameComponent.gameStateService();

        // Créer la ville via la factory
        City city = gameStartupFactory.createCity(primaryStage);
        if (city == null) {
            System.exit(0);
            return;
        }

        // Initialiser le service d'état avec la ville
        gameStateService.setCity(city);

        // Créer la vue et le contrôleur
        GameView view = new GameView(city, gameStateService);
        Scene scene = new Scene(view);
        primaryStage.setTitle("City Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
