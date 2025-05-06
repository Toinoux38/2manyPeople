package com.citybuilder.ui;

import com.citybuilder.controller.GameController;
import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.Sub;
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
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.concurrent.Flow.Subscriber;

public class GameView implements Subscriber<GameEvent> {
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
        
        // Initialiser la grille avec les dimensions de la ville
        Cell[][] map = controller.getCity().getMap();
        this.cells = new Rectangle[map.length][map[0].length];
        
        initializeGrid();
        setupGridInteraction();
        
        // S'enregistrer comme subscriber
        controller.subscribe(this);
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
        
        Label cityNameLabel = new Label();
        cityNameLabel.textProperty().bind(Bindings.concat("Ville: ", controller.getCityName()));
        
        Label moneyLabel = new Label();
        moneyLabel.textProperty().bind(Bindings.concat("Argent: ", controller.getMoney()));
        
        Label populationLabel = new Label();
        populationLabel.textProperty().bind(Bindings.concat("Population: ", controller.getTotalPopulation()));
        
        Label workersLabel = new Label();
        workersLabel.textProperty().bind(Bindings.concat("Travailleurs: ", controller.getTotalWorkers()));
        
        Label satisfactionLabel = new Label();
        satisfactionLabel.textProperty().bind(Bindings.concat("Satisfaction: ", controller.getAverageSatisfaction()));
        
        Label hazardRateLabel = new Label();
        hazardRateLabel.textProperty().bind(Bindings.concat("Taux de risque: ", controller.getHazardRate()));
        
        statsBar.getChildren().addAll(
            cityNameLabel, moneyLabel, populationLabel, 
            workersLabel, satisfactionLabel, hazardRateLabel
        );
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
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> notificationArea.getChildren().remove(notification));
        pause.play();
    }

    private void initializeGrid() {
        gridPane.setGridLinesVisible(true);
        Cell[][] map = controller.getCity().getMap();
        
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                Rectangle cell = new Rectangle(20, 20);
                cell.setFill(getColorForCell(map[x][y]));
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
        
        Cell[][] map = controller.getCity().getMap();
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            lastX = x;
            lastY = y;
            handleCellClick(x, y);
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        
        Cell[][] map = controller.getCity().getMap();
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && 
            (selectedTool.equals("ROAD") || selectedTool.equals("POWER_POLE") || selectedTool.equals("RESIDENTIAL"))) {
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
            handleCellClick(x1, y1);
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

    private void handleCellClick(int x, int y) {
        if (selectedTool.equals("BULLDOZER")) {
            controller.removeCell(x, y);
        } else {
            controller.placeCell(x, y, selectedTool);
        }
    }

    private Color getColorForCell(Cell cell) {
        if (cell == null) return Color.BLACK;
        if (cell.getIsWater()) return Color.BLUE;
        
        switch (cell.getType()) {
            case ROAD:
                return Color.GRAY;
            case RESIDENTIAL:
                return cell.hasPower() ? Color.LIGHTBLUE : Color.DARKBLUE;
            case INDUSTRIAL:
                return cell.hasPower() ? Color.ORANGE : Color.DARKRED;
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
    public void onNext(GameEvent event) {
        // Mettre à jour la vue en fonction de l'événement
        Cell[][] map = controller.getCity().getMap();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                cells[x][y].setFill(getColorForCell(map[x][y]));
            }
        }
        showNotification("Événement: " + event.getType());
    }

    @Override
    public void onSubscribe(java.util.concurrent.Flow.Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onError(Throwable throwable) {
        showNotification("Erreur: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        showNotification("Simulation terminée");
    }
} 