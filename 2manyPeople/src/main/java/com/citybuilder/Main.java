package com.citybuilder;

import com.citybuilder.controller.GameController;
import com.citybuilder.factory.DefaultGameStartupFactory;
import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.modelBis.City;
import com.citybuilder.ui.GameStartupDialog;
import com.citybuilder.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameStartupFactory gameStartupFactory;

    @Override
    public void init() {
        // Initialiser la factory
        gameStartupFactory = new DefaultGameStartupFactory();
    }

    @Override
    public void start(Stage primaryStage) {
        // Créer et afficher la boîte de dialogue de démarrage
        GameStartupDialog dialog = new GameStartupDialog(primaryStage, gameStartupFactory);
        City city = dialog.showAndWait();
        
        if (city != null) {
            // Créer le contrôleur avec la nouvelle ville
            GameController controller = new GameController(city);
            
            // Créer la vue
            GameView gameView = new GameView(controller);
            
            // Configurer la scène
            Scene scene = new Scene(gameView.getRoot(), 1200, 800);
            
            // Configurer la fenêtre
            primaryStage.setTitle("City Builder - " + city.getName());
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
