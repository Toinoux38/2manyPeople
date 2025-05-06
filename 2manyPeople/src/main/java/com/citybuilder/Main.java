// 2ManyPeople - 2025 - youmix27 & toinoux38
package com.citybuilder;

import com.citybuilder.factory.DefaultGameStartupFactory;
import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.modelBis.City;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Créer les services
        GameStartupFactory gameStartupFactory = new DefaultGameStartupFactory();
        GameStateService gameStateService = new GameStateService();

        // Créer la ville via la factory
        City city = gameStartupFactory.createCity("Ma Ville", 0.1f);
        if (city == null) {
            System.exit(0);
            return;
        }

        // Initialiser le service d'état avec la ville
        gameStateService.setCity(city);

        // Créer la vue
        GameView view = new GameView(city, gameStateService);
        Scene scene = new Scene(view.getRoot());
        primaryStage.setTitle("City Builder - " + city.getName());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
