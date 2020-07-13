package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameMap {

    private int width = 53;
    private int height = 30;   
    private int tileSize = 16;
    private char[][] grid = new char[width][height];
    private MapList data = new MapList();

    public GameMap(String name) {
        load(name);
    }

    private void load(String name) {
        String[] m = data.maps.get(name);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = m[y].charAt(x);
            }

        }

    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(1, 1, 1, 1);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(grid[x][y] != '-') {
                    if(inBounds(x + 1, y) && grid[x + 1][y] == '-') {
                        shape.line((x + 1) * tileSize, y * tileSize, (x + 1) * tileSize, y * tileSize + tileSize);
                    }
                    if(inBounds(x - 1, y) && grid[x - 1][y] == '-') {
                        shape.line(x * tileSize, y * tileSize, x * tileSize, y * tileSize + tileSize);
                    }
                    if(inBounds(x, y + 1) && grid[x][y + 1] == '-') {
                        shape.line(x * tileSize, (y + 1) * tileSize, x * tileSize + tileSize, (y + 1) * tileSize);
                    }
                    if(inBounds(x, y - 1) && grid[x][y - 1] == '-') {
                        shape.line(x * tileSize, y * tileSize, x * tileSize + tileSize, y * tileSize);
                    }
                }
            }
        }
    }

    private boolean inBounds(int x, int y) {
        if(x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        else {
            return true;
        }
    }
}