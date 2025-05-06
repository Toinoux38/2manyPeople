package com.citybuilder.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CityConfigDialog {
    private String cityName;
    private float hazardRate;

    public CityConfigDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Configuration de la ville");
        dialog.setHeaderText("Configurez votre nouvelle ville");

        // Boutons
        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Contenu
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom de la ville");
        Slider hazardSlider = new Slider(0, 1, 0.1);
        hazardSlider.setShowTickLabels(true);
        hazardSlider.setShowTickMarks(true);
        hazardSlider.setMajorTickUnit(0.1);
        hazardSlider.setBlockIncrement(0.1);

        grid.add(new Label("Nom de la ville:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Taux de risque:"), 0, 1);
        grid.add(hazardSlider, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Conversion du résultat
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                cityName = nameField.getText();
                hazardRate = (float) hazardSlider.getValue();
                return createButtonType;
            }
            return null;
        });

        dialog.showAndWait();
    }

    public String getCityName() {
        return cityName;
    }

    public float getHazardRate() {
        return hazardRate;
    }
} 