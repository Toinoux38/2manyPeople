package com.citybuilder.ui;

import com.citybuilder.modelBis.Cell;
import com.citybuilder.modelBis.CellType;
import com.citybuilder.modelBis.City;
import com.citybuilder.modelBis.events.GameEvent;
import com.citybuilder.service.GameStateService;
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
import javafx.geometry.Insets;

import java.util.concurrent.Flow.Subscriber;

public class GameView extends BorderPane implements Subscriber<GameEvent> {
    private final City city;
    private final GameStateService gameStateService;
    private CellType selectedTool = CellType.ROAD;
    private final GridPane gridPane;
    private final VBox root;
    private final ToolBar toolbar;
    private final HBox statsBar;
    private final VBox notificationArea;
    private Rectangle[][] cells;
    private int lastX = -1;
    private int lastY = -1;
    private final Label cityNameLabel;
    private final Label moneyLabel;
    private final Label populationLabel;
    private final Label workersLabel;
    private final Label satisfactionLabel;
    private final Label hazardLabel;
    private static final int CELL_SIZE = 20;
    private static final int MIN_WINDOW_WIDTH = 800;
    private static final int MIN_WINDOW_HEIGHT = 600;

    public GameView(City city, GameStateService gameStateService) {
        this.city = city;
        this.gameStateService = gameStateService;
        
        // Créer les labels pour les statistiques
        cityNameLabel = new Label("Ville: " + city.getName());
        moneyLabel = new Label("Argent: " + city.getMoney());
        populationLabel = new Label("Population: " + getTotalPopulation());
        workersLabel = new Label("Travailleurs: " + getTotalWorkers());
        satisfactionLabel = new Label("Satisfaction: " + getAverageSatisfaction() + "%");
        hazardLabel = new Label("Risque: " + city.getHazardRate() + "%");
        
        // Créer les composants UI
        this.gridPane = new GridPane();
        this.toolbar = createToolbar();
        this.statsBar = createStatsBar();
        this.notificationArea = createNotificationArea();
        
        // Créer le layout principal
        this.root = new VBox(10); // 10 pixels d'espacement
        this.root.setPadding(new Insets(10));
        this.root.setMinSize(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        
        // Initialiser la grille avec les dimensions de la ville
        Cell[][] map = city.getMap();
        this.cells = new Rectangle[map.length][map[0].length];
        
        // Configurer le layout principal
        VBox leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setMinWidth(200);
        leftPanel.getChildren().addAll(
            cityNameLabel, moneyLabel, populationLabel, 
            workersLabel, satisfactionLabel, hazardLabel
        );
        
        // Configurer la grille
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(10));
        gridPane.setGridLinesVisible(true);
        
        // Ajouter les composants à la vue
        setTop(toolbar);
        setLeft(leftPanel);
        setCenter(gridPane);
        setBottom(notificationArea);
        
        // Configurer les contraintes de redimensionnement
        HBox.setHgrow(gridPane, Priority.ALWAYS);
        VBox.setVgrow(gridPane, Priority.ALWAYS);
        
        initializeGrid();
        setupGridInteraction();
        
        // S'enregistrer comme subscriber
        gameStateService.subscribe(this);
    }

    private ToolBar createToolbar() {
        ToolBar toolbar = new ToolBar();
        toolbar.setPadding(new Insets(5));
        
        // Créer les boutons pour chaque type de construction
        for (CellType type : CellType.values()) {
            if (type != CellType.EMPTY) {
                Button button = new Button(type.name());
                button.setMinWidth(100);
                button.setOnAction(e -> selectedTool = type);
                toolbar.getItems().add(button);
            }
        }
        
        return toolbar;
    }

    private HBox createStatsBar() {
        HBox statsBar = new HBox(10);
        statsBar.setPadding(new Insets(5));
        statsBar.setAlignment(Pos.CENTER_LEFT);
        
        statsBar.getChildren().addAll(
            cityNameLabel, moneyLabel, populationLabel, 
            workersLabel, satisfactionLabel, hazardLabel
        );
        return statsBar;
    }

    private VBox createNotificationArea() {
        VBox notificationArea = new VBox(5);
        notificationArea.setPadding(new Insets(5));
        notificationArea.setAlignment(Pos.BOTTOM_LEFT);
        notificationArea.setMinHeight(50);
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
        Cell[][] map = city.getMap();
        
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
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
        
        Cell[][] map = city.getMap();
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length) {
            lastX = x;
            lastY = y;
            handleCellClick(x, y);
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        
        Cell[][] map = city.getMap();
        if (x >= 0 && x < map.length && y >= 0 && y < map[0].length && 
            (selectedTool == CellType.ROAD || selectedTool == CellType.POWER_POLE || selectedTool == CellType.RESIDENTIAL)) {
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
        Cell cell = city.getMap()[x][y];
        if (cell != null) {
            if (selectedTool == CellType.EMPTY) {
                gameStateService.destroy(cell);
            } else {
                gameStateService.purchase(selectedTool, cell);
            }
            updateGrid();
        }
    }

    private void updateGrid() {
        Cell[][] map = city.getMap();
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                cells[x][y].setFill(getColorForCell(map[x][y]));
            }
        }
        
        // Mettre à jour les statistiques
        moneyLabel.setText("Argent: " + city.getMoney());
        populationLabel.setText("Population: " + getTotalPopulation());
        workersLabel.setText("Travailleurs: " + getTotalWorkers());
        satisfactionLabel.setText("Satisfaction: " + getAverageSatisfaction() + "%");
    }

    private Color getColorForCell(Cell cell) {
        if (cell == null) return Color.GRAY;
        
        if (cell.getIsWater()) return Color.BLUE;
        
        switch (cell.getType()) {
            case ROAD:
                return Color.DARKGRAY;
            case RESIDENTIAL:
                return cell.hasPower() ? Color.GREEN : Color.DARKGREEN;
            case INDUSTRIAL:
                return cell.hasPower() ? Color.ORANGE : Color.DARKORANGE;
            case POWER_PLANT:
                return Color.RED;
            case POWER_POLE:
                return Color.YELLOW;
            case POLICE_STATION:
                return Color.BLUE;
            case FIRE_STATION:
                return Color.RED;
            default:
                return Color.LIGHTGRAY;
        }
    }

    private int getTotalPopulation() {
        int total = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null) {
                    total += cell.getPopulation();
                }
            }
        }
        return total;
    }
    
    private int getTotalWorkers() {
        int total = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null) {
                    total += cell.getWorkers();
                }
            }
        }
        return total;
    }
    
    private int getAverageSatisfaction() {
        int total = 0;
        int count = 0;
        Cell[][] map = city.getMap();
        for (Cell[] cells : map) {
            for (Cell cell : cells) {
                if (cell != null && cell.getType() == CellType.RESIDENTIAL) {
                    total += cell.getSatisfaction();
                    count++;
                }
            }
        }
        return count > 0 ? total / count : 0;
    }

    public VBox getRoot() {
        return root;
    }

    @Override
    public void onNext(GameEvent event) {
        updateGrid();
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