package com.citybuilder;

import javafx.scene.Scene;
import javafx.stage.Stage;
import com.citybuilder.ui.GameView;
import com.citybuilder.model.World;

public class Game {
    private final Stage primaryStage;
    private final World world;
    private final GameView gameView;
    private final SimulationEngine simulationEngine;

    public Game(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.world = new World(50, 50); // Grille de 50x50
        this.simulationEngine = new SimulationEngine(world);
        this.gameView = new GameView(world, simulationEngine);
    }

    public void start() {
        primaryStage.setTitle("City Builder");
        Scene scene = new Scene(gameView.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Démarrer la simulation
        simulationEngine.start();
    }
} 