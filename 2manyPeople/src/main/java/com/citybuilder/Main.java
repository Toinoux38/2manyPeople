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
        // Initialiser Dagger avec le Stage
        gameComponent = DaggerGameComponent.builder()
            .gameModule(new GameModule(primaryStage))
            .build();

        // Obtenir la boîte de dialogue de démarrage
        GameStartupDialog dialog = gameComponent.gameStartupDialog();
        
        // Créer la ville via la boîte de dialogue
        City city = dialog.showAndWait();
        if (city == null) {
            System.exit(0);
            return;
        }

        // Initialiser le service d'état avec la ville
        GameStateService gameStateService = gameComponent.gameStateService();
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
