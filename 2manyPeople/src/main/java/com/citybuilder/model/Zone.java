package com.citybuilder.model;

public abstract class Zone extends Building {
    protected static final int ZONE_SIZE = 4;
    protected boolean[][] occupiedCells;
    private final int size;

    public Zone(int x, int y, String type, int size) {
        super(x, y, type);
        this.occupiedCells = new boolean[ZONE_SIZE][ZONE_SIZE];
        for (int i = 0; i < ZONE_SIZE; i++) {
            for (int j = 0; j < ZONE_SIZE; j++) {
                occupiedCells[i][j] = true;
            }
        }
        this.size = size;
    }

    public int getZoneSize() {
        return ZONE_SIZE;
    }

    public boolean isCellOccupied(int localX, int localY) {
        return occupiedCells[localX][localY];
    }

    public boolean isAdjacentToRoad(World world) {
        // Vérifie si au moins une cellule de la zone est adjacente à une route
        for (int i = 0; i < ZONE_SIZE; i++) {
            for (int j = 0; j < ZONE_SIZE; j++) {
                if (isAdjacentToRoad(world, x + i, y + j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isInPowerRange(World world) {
        // Vérifie si au moins une cellule de la zone est dans le rayon d'une source d'électricité
        for (int i = 0; i < ZONE_SIZE; i++) {
            for (int j = 0; j < ZONE_SIZE; j++) {
                if (isInPowerRange(world, x + i, y + j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAdjacentToRoad(World world, int x, int y) {
        return world.getTile(x-1, y) instanceof Road ||
               world.getTile(x+1, y) instanceof Road ||
               world.getTile(x, y-1) instanceof Road ||
               world.getTile(x, y+1) instanceof Road;
    }

    private boolean isInPowerRange(World world, int x, int y) {
        for (Building building : world.getBuildings()) {
            if (building instanceof PowerPlant || building instanceof PowerPole) {
                int dx = Math.abs(x - building.getX());
                int dy = Math.abs(y - building.getY());
                int distance = dx + dy;
                
                int powerRadius = (building instanceof PowerPlant) ? 
                    ((PowerPlant) building).getPowerRadius() : 
                    ((PowerPole) building).getPowerRadius();
                    
                if (distance <= powerRadius) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void update(World world) {
        // Vérifier l'accès aux routes et à l'électricité
        setHasRoadAccess(checkRoadAccess(world));
        setHasPower(checkPowerAccess(world));
    }

    private boolean checkRoadAccess(World world) {
        // Vérifier si la zone a accès à une route
        for (int dx = -1; dx <= size; dx++) {
            for (int dy = -1; dy <= size; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;
                if (checkX >= 0 && checkX < world.getWidth() && 
                    checkY >= 0 && checkY < world.getHeight()) {
                    Tile tile = world.getTile(checkX, checkY);
                    if (tile instanceof Road) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkPowerAccess(World world) {
        // Vérifier si la zone a accès à l'électricité
        for (int dx = -1; dx <= size; dx++) {
            for (int dy = -1; dy <= size; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;
                if (checkX >= 0 && checkX < world.getWidth() && 
                    checkY >= 0 && checkY < world.getHeight()) {
                    Tile tile = world.getTile(checkX, checkY);
                    if (tile instanceof PowerPole || tile instanceof PowerPlant) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
} 