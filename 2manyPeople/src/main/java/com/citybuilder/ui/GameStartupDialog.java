package com.citybuilder.ui;

import com.citybuilder.factory.GameStartupFactory;
import com.citybuilder.modelBis.City;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameStartupDialog {
    private final GameStartupFactory factory;
    private City createdCity;
    private final Stage dialogStage;

    public GameStartupDialog(Stage parent, GameStartupFactory factory) {
        this.factory = factory;
        this.dialogStage = new Stage();
        this.dialogStage.initModality(Modality.APPLICATION_MODAL);
        this.dialogStage.initOwner(parent);
        this.dialogStage.setTitle("Nouvelle Ville");
        
        setupUI();
    }

    private void setupUI() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Nom de la ville
        Label nameLabel = new Label("Nom de la ville:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        // Taux de risque
        Label hazardLabel = new Label("Taux de risque (0.0 - 1.0):");
        Spinner<Double> hazardSpinner = new Spinner<>(
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 1.0, 0.1, 0.1)
        );
        grid.add(hazardLabel, 0, 1);
        grid.add(hazardSpinner, 1, 1);

        // Boutons
        ButtonBar buttonBar = new ButtonBar();
        Button createButton = new Button("Créer");
        Button cancelButton = new Button("Annuler");
        buttonBar.getButtons().addAll(createButton, cancelButton);
        grid.add(buttonBar, 0, 2, 2, 1);

        // Actions des boutons
        createButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez entrer un nom de ville");
                alert.showAndWait();
                return;
            }

            double hazardRate = hazardSpinner.getValue();
            createdCity = factory.createCity(name, (float) hazardRate);
            dialogStage.close();
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        Scene scene = new Scene(grid);
        dialogStage.setScene(scene);
    }

    public City showAndWait() {
        dialogStage.showAndWait();
        return createdCity;
    }
} 