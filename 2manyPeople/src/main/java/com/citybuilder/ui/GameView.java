package com.citybuilder.ui;

import com.citybuilder.model.World;
import com.citybuilder.model.Tile;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Node;

public class GameView {
    private final World world;
    private final GridPane gridPane;
    private final VBox root;
    private final ToolBar toolbar;
    private String selectedTool = "ROAD";
    private Rectangle[][] cells;

    public GameView(World world) {
        this.world = world;
        this.gridPane = new GridPane();
        this.toolbar = createToolbar();
        this.root = new VBox(toolbar, gridPane);
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
        
        Button bulldozerButton = new Button("Bulldozer");
        bulldozerButton.setOnAction(e -> selectedTool = "BULLDOZER");
        
        toolbar.getItems().addAll(roadButton, residentialButton, bulldozerButton);
        return toolbar;
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