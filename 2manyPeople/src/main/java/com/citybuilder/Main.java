package com.citybuilder;

import com.citybuilder.controller.GameController;
import com.citybuilder.model.World;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Créer le monde (modèle)
        World world = new World(50, 50); // 50x50 grid
        
        // Créer le contrôleur
        GameController controller = new GameController(world);
        
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
