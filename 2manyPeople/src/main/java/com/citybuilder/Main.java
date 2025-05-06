package com.citybuilder;

import com.citybuilder.controller.GameController;
import com.citybuilder.module.DaggerGameComponent;
import com.citybuilder.module.GameComponent;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;

public class Main extends Application {
    private GameComponent gameComponent;

    @Override
    public void init() {
        gameComponent = DaggerGameComponent.create();
    }

    @Override
    public void start(Stage primaryStage) {
        // Obtenir le contrôleur via Dagger
        GameController controller = gameComponent.provideGameController();
        
        // Créer la vue
        GameView gameView = new GameView(controller);
        
        // Configurer la scène
        Scene scene = new Scene(gameView.getRoot(), 1200, 800);
        
        // Configurer la fenêtre
        primaryStage.setTitle("City Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
