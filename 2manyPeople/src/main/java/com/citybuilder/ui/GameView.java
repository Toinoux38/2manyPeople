package com.citybuilder.ui;

import com.citybuilder.controller.GameController;
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
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameView implements WorldObserver {
    private final GameController controller;
    private final GridPane gridPane;
    private final VBox root;
    private final ToolBar toolbar;
    private final HBox statsBar;
    private final VBox notificationArea;
    private String selectedTool = "ROAD";
    private Rectangle[][] cells;
    private int lastX = -1;
    private int lastY = -1;

    public GameView(GameController controller) {
        this.controller = controller;
        this.gridPane = new GridPane();
        this.toolbar = createToolbar();
        this.statsBar = createStatsBar();
        this.notificationArea = createNotificationArea();
        
        // Créer le layout principal
        this.root = new VBox(toolbar, statsBar, gridPane, notificationArea);
        this.cells = new Rectangle[controller.getWorld().getWidth()][controller.getWorld().getHeight()];
        
        initializeGrid();
        setupGridInteraction();
        
        // S'enregistrer comme observateur
        controller.addObserver(this);
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
        
        Button policeStationButton = new Button("Commissariat");
        policeStationButton.setOnAction(e -> selectedTool = "POLICE_STATION");
        
        Button fireStationButton = new Button("Caserne de Pompiers");
        fireStationButton.setOnAction(e -> selectedTool = "FIRE_STATION");
        
        Button bulldozerButton = new Button("Bulldozer");
        bulldozerButton.setOnAction(e -> selectedTool = "BULLDOZER");
        
        toolbar.getItems().addAll(
            roadButton, residentialButton, industrialButton, 
            powerPlantButton, powerPoleButton, policeStationButton,
            fireStationButton, bulldozerButton
        );
        return toolbar;
    }

    private HBox createStatsBar() {
        HBox statsBar = new HBox(10);
        statsBar.setPadding(new javafx.geometry.Insets(5));
        
        Label populationLabel = new Label();
        populationLabel.textProperty().bind(Bindings.concat("Population: ", controller.getWorld().totalPopulationProperty()));
        
        Label workersLabel = new Label();
        workersLabel.textProperty().bind(Bindings.concat("Travailleurs: ", controller.getWorld().totalWorkersProperty()));
        
        Label satisfactionLabel = new Label();
        satisfactionLabel.textProperty().bind(Bindings.concat("Satisfaction: ", controller.getWorld().satisfactionRateProperty()));
        
        statsBar.getChildren().addAll(populationLabel, workersLabel, satisfactionLabel);
        return statsBar;
    }

    private VBox createNotificationArea() {
        VBox notificationArea = new VBox(5);
        notificationArea.setPadding(new javafx.geometry.Insets(5));
        notificationArea.setAlignment(Pos.BOTTOM_LEFT);
        return notificationArea;
    }

    public void showNotification(String message) {
        Text notification = new Text(message);
        notificationArea.getChildren().add(notification);
        
        // Supprimer la notification après 5 secondes
        new javafx.animation.PauseTransition(javafx.util.Duration.seconds(5))
            .setOnFinished(e -> notificationArea.getChildren().remove(notification))
            .play();
    }

    private void initializeGrid() {
        gridPane.setGridLinesVisible(true);
        
        for (int x = 0; x < controller.getWorld().getWidth(); x++) {
            for (int y = 0; y < controller.getWorld().getHeight(); y++) {
                Rectangle cell = new Rectangle(20, 20);
                cell.setFill(getColorForTile(controller.getWorld().getTile(x, y)));
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
        
        if (x >= 0 && x < controller.getWorld().getWidth() && y >= 0 && y < controller.getWorld().getHeight()) {
            lastX = x;
            lastY = y;
            handleTileClick(x, y);
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        
        if (x >= 0 && x < controller.getWorld().getWidth() && y >= 0 && y < controller.getWorld().getHeight() && 
            (selectedTool.equals("ROAD") || selectedTool.equals("POWER_POLE"))) {
            if (lastX != -1 && lastY != -1) {
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
        if (selectedTool.equals("BULLDOZER")) {
            controller.removeTile(x, y);
        } else {
            controller.placeTile(x, y, selectedTool);
        }
    }

    private Color getColorForTile(Tile tile) {
        if (tile == null) return Color.BLACK;
        
        switch (tile.getType()) {
            case ROAD:
                return Color.GRAY;
            case RESIDENTIAL:
                return Color.BLUE;
            case INDUSTRIAL:
                return Color.RED;
            case POWER_PLANT:
                return Color.YELLOW;
            case POWER_POLE:
                return Color.ORANGE;
            case POLICE_STATION:
                return Color.DARKBLUE;
            case FIRE_STATION:
                return Color.DARKRED;
            case EMPTY:
                return Color.rgb(34, 139, 34); // Forest Green
            default:
                return Color.BLACK;
        }
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        showNotification(event.getMessage());
    }

    @Override
    public void update() {
        for (int x = 0; x < controller.getWorld().getWidth(); x++) {
            for (int y = 0; y < controller.getWorld().getHeight(); y++) {
                cells[x][y].setFill(getColorForTile(controller.getWorld().getTile(x, y)));
            }
        }
    }
} 