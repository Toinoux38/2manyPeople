package com.citybuilder.ui;

import com.citybuilder.SimulationEngine;
import com.citybuilder.model.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.beans.binding.Bindings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GameView {
    private final World world;
    private final GridPane gridPane;
    private final VBox root;
    private final ToolBar toolbar;
    private final HBox statsBar;
    private String selectedTool = "ROAD";
    private Rectangle[][] cells;
    private final SimulationEngine simulationEngine;
    private int lastX = -1;
    private int lastY = -1;
    private final Image grassTexture;

    public GameView(World world, SimulationEngine simulationEngine) {
        this.world = world;
        this.simulationEngine = simulationEngine;
        this.gridPane = new GridPane();
        this.toolbar = createToolbar();
        this.statsBar = createStatsBar();
        
        // Créer un StackPane pour superposer la grille et l'image de fond
        StackPane gameArea = new StackPane();
        Image grassTexture = new Image("grass.png");
        ImageView background = new ImageView(grassTexture);
        background.setFitWidth(800);
        background.setFitHeight(600);
        gameArea.getChildren().addAll(background, gridPane);
        
        // Créer le layout principal
        this.root = new VBox(toolbar, statsBar, gameArea);
        this.cells = new Rectangle[world.getWidth()][world.getHeight()];
        this.grassTexture = new Image("grass.png");
        
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
        gridPane.setOnMousePressed(this::handleMousePressed);
        gridPane.setOnMouseDragged(this::handleMouseDragged);
        gridPane.setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        
        if (x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
            lastX = x;
            lastY = y;
            handleTileClick(x, y);
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        
        if (x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight() && 
            (selectedTool.equals("ROAD") || selectedTool.equals("POWER_POLE"))) {
            if (lastX != -1 && lastY != -1) {
                // Dessiner une ligne droite
                drawLine(lastX, lastY, x, y);
            }
            lastX = x;
            lastY = y;
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        lastX = -1;
        lastY = -1;
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            handleTileClick(x1, y1);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
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
                // Vérifier si la tuile est un bâtiment ou une route
                Tile currentTile = world.getTile(x, y);
                if (currentTile instanceof Building || currentTile instanceof Road) {
                    world.removeTile(x, y);
                }
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
                // Utiliser une couleur verte pour simuler l'herbe
                return Color.rgb(34, 139, 34); // Forest Green
            default:
                return Color.BLACK;
        }
    }

    public VBox getRoot() {
        return root;
    }
} 