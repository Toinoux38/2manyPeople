package com.citybuilder.ui;

import com.citybuilder.model.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.beans.binding.Bindings;

public class GameView {
    private final World world;
    private final GridPane gridPane;
    private final VBox root;
    private final ToolBar toolbar;
    private final HBox statsBar;
    private String selectedTool = "ROAD";
    private Rectangle[][] cells;
    private final SimulationEngine simulationEngine;

    public GameView(World world, SimulationEngine simulationEngine) {
        this.world = world;
        this.simulationEngine = simulationEngine;
        this.gridPane = new GridPane();
        this.toolbar = createToolbar();
        this.statsBar = createStatsBar();
        this.root = new VBox(toolbar, statsBar, gridPane);
        this.cells = new Rectangle[world.getWidth()][world.getHeight()];
        
        initializeGrid();
        setupGridInteraction();
    }

    private ToolBar createToolbar() {
        ToolBar toolbar = new ToolBar();
        
        Button roadButton = new Button("Route");
        roadButton.setOnAction(e -> selectedTool = "ROAD");
        
        Button residentialButton = new Button("Zone Résidentielle");
        residentialButton.setOnAction(e -> selectedTool = "RESIDENTIAL");
        
        Button industrialButton = new Button("Zone Industrielle");
        industrialButton.setOnAction(e -> selectedTool = "INDUSTRIAL");
        
        Button powerPlantButton = new Button("Centrale Électrique");
        powerPlantButton.setOnAction(e -> selectedTool = "POWER_PLANT");
        
        Button powerPoleButton = new Button("Pylône Électrique");
        powerPoleButton.setOnAction(e -> selectedTool = "POWER_POLE");
        
        Button bulldozerButton = new Button("Bulldozer");
        bulldozerButton.setOnAction(e -> selectedTool = "BULLDOZER");
        
        toolbar.getItems().addAll(roadButton, residentialButton, industrialButton, 
                                powerPlantButton, powerPoleButton, bulldozerButton);
        return toolbar;
    }

    private HBox createStatsBar() {
        HBox statsBar = new HBox(10);
        statsBar.setPadding(new javafx.geometry.Insets(5));
        
        Label populationLabel = new Label();
        populationLabel.textProperty().bind(Bindings.concat("Population: ", simulationEngine.totalPopulationProperty()));
        
        Label workersLabel = new Label();
        workersLabel.textProperty().bind(Bindings.concat("Travailleurs: ", simulationEngine.totalWorkersProperty()));
        
        Label satisfactionLabel = new Label();
        satisfactionLabel.textProperty().bind(Bindings.concat("Satisfaction: ", simulationEngine.satisfactionRateProperty()));
        
        statsBar.getChildren().addAll(populationLabel, workersLabel, satisfactionLabel);
        return statsBar;
    }

    private void initializeGrid() {
        gridPane.setGridLinesVisible(true);
        
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Rectangle cell = new Rectangle(20, 20);
                cell.setFill(getColorForTile(world.getTile(x, y)));
                cells[x][y] = cell;
                gridPane.add(cell, x, y);
            }
        }
    }

    private void setupGridInteraction() {
        gridPane.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / 20);
            int y = (int) (event.getY() / 20);
            
            if (x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                handleTileClick(x, y);
            }
        });
    }

    private void handleTileClick(int x, int y) {
        switch (selectedTool) {
            case "ROAD":
                world.placeTile(x, y, new Road(x, y));
                break;
            case "RESIDENTIAL":
                world.placeTile(x, y, new ResidentialZone(x, y));
                break;
            case "INDUSTRIAL":
                world.placeTile(x, y, new IndustrialZone(x, y));
                break;
            case "POWER_PLANT":
                world.placeTile(x, y, new PowerPlant(x, y));
                break;
            case "POWER_POLE":
                world.placeTile(x, y, new PowerPole(x, y));
                break;
            case "BULLDOZER":
                world.removeTile(x, y);
                break;
        }
        updateGrid();
    }

    private void updateGrid() {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                cells[x][y].setFill(getColorForTile(world.getTile(x, y)));
            }
        }
    }

    private Color getColorForTile(Tile tile) {
        switch (tile.getType()) {
            case "ROAD":
                return Color.GRAY;
            case "RESIDENTIAL":
                return Color.BLUE;
            case "INDUSTRIAL":
                return Color.RED;
            case "POWER_PLANT":
                return Color.YELLOW;
            case "POWER_POLE":
                return Color.ORANGE;
            case "EMPTY":
                return Color.WHITE;
            default:
                return Color.BLACK;
        }
    }

    public VBox getRoot() {
        return root;
    }
} 