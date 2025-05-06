// 2ManyPeople - 2025 - youmix27 & toinoux38
package com.citybuilder;

import com.citybuilder.di.DaggerGameComponent;
import com.citybuilder.di.GameComponent;
import com.citybuilder.di.GameModule;
import com.citybuilder.modelBis.City;
import com.citybuilder.service.GameStateService;
import com.citybuilder.ui.GameStartupDialog;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameComponent gameComponent;

    @Override
    public void start(Stage primaryStage) {
        // Initialiser Dagger
        gameComponent = DaggerGameComponent.builder()
                .gameModule(new GameModule(primaryStage))
                .build();

        // Créer la ville via le dialogue de démarrage
        GameStartupDialog dialog = gameComponent.getGameStartupDialog();
        City city = dialog.showAndWait();

        if (city == null) {
            System.exit(0);
            return;
        }

        // Créer la vue du jeu
        GameView gameView = new GameView(city, gameComponent.getGameStateService());
        
        // Créer la scène
        Scene scene = new Scene(gameView.getRoot());
        
        // Configurer la fenêtre principale
        primaryStage.setTitle("City Builder - " + city.getName());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
