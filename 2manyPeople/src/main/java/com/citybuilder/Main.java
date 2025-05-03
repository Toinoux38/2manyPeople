package com.citybuilder;

import com.citybuilder.controller.GameController;
import com.citybuilder.model.World;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int WORLD_WIDTH = 40;
    private static final int WORLD_HEIGHT = 30;

    @Override
    public void start(Stage primaryStage) {
        // Créer le monde
        World world = new World(WORLD_WIDTH, WORLD_HEIGHT);
        
        // Créer la vue
        GameView view = new GameView(null); // Le contrôleur sera passé après sa création
        
        // Créer le contrôleur
        GameController controller = new GameController(world, view);
        
        // Mettre à jour la vue avec le contrôleur
        view = new GameView(controller);
        
        // Configurer la scène
        Scene scene = new Scene(view.getRoot(), 800, 600);
        primaryStage.setTitle("2manyPeople - City Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Démarrer la simulation
        controller.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
} 