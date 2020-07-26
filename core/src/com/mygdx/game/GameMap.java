package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameMap {

    private int width = 53;
    private int height = 30;   
    private int tileSize = 16;
    private char[][] grid = new char[width][height];
    private MapList data = new MapList();
    private ArrayList<Vector2> starts = new ArrayList<Vector2>();
    private Vector2 end;

    public GameMap(String name) {
        load(name);
    }

    private void load(String name) {
        String[] m = data.getMap(name);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                grid[x][y] = m[y].charAt(x);
                if(m[y].charAt(x) == 's') {
                    starts.add(new Vector2(x,  y));
                }
                if(m[y].charAt(x) == 'e') {
                    end = new Vector2(x, y);
                }
            }

        }
    }

    public ArrayList<Vector2> getStarts() {
        return starts;
    }

    public Vector2 getEnd() {
        return end;
    }

    public int getTileSize() {
        return tileSize;
    }

    public boolean open(int x, int y) {
        if(inBounds(x, y) && grid[x][y] == '-') {
            return true;
        }
        return false;
    }

    public void testDraw(BitmapFont font, SpriteBatch batch) {
        int[][] path = pathFind(3, 29, 13, 6);
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(path[x][y] != 999) {
                    font.draw(batch, "" + path[x][y], x * 16 + 4, y * 16 + 4);
                }
            }
        }
    }

    /**
     * Draws map with lines
     * @param shape
     */

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

    /**
     * This method finds a path by flood filling map array starting at target position and reaching start position.
     * @param x1 Starting x coordinate
     * @param y1 Starting y coordinate
     * @param x2 Target x coordinate
     * @param y2 Target y coordinate
     * @return Array with path (Starting at total length and ending at 0). Non-path positions are 999.
     */

    public int[][] pathFind(int x1, int y1, int x2, int y2) {
        int[][] pathMap = new int[width][height];
        for(int[] row: pathMap) {
            Arrays.fill(row, 999);
        }
        ArrayList<Vector2> queue = new ArrayList<Vector2>();
        int count = 0;
        pathMap[x2][y2] = count;
        queue.add(new Vector2(x2, y2));
        while(queue.size() > 0) {
            count++;
            Vector2 point = queue.remove(0);
            if((int)point.x == x1 && (int)point.y == y1) {
                break;
            }
            if(inBounds((int)point.x - 1, (int)point.y) && grid[(int)point.x - 1][(int)point.y] != '-' && pathMap[(int)point.x - 1][(int)point.y] == 999) {
                pathMap[(int)point.x - 1][(int)point.y] = count;
                queue.add(new Vector2(point.x - 1, point.y));
            }
            if(inBounds((int)point.x + 1, (int)point.y) && grid[(int)point.x + 1][(int)point.y] != '-' && pathMap[(int)point.x + 1][(int)point.y] == 999) {
                pathMap[(int)point.x + 1][(int)point.y] = count;
                queue.add(new Vector2(point.x + 1, point.y));
            }
            if(inBounds((int)point.x, (int)point.y - 1) && grid[(int)point.x][(int)point.y - 1] != '-' && pathMap[(int)point.x][(int)point.y - 1] == 999) {
                pathMap[(int)point.x][(int)point.y - 1] = count;
                queue.add(new Vector2(point.x, point.y - 1));
            }
            if(inBounds((int)point.x, (int)point.y + 1) && grid[(int)point.x][(int)point.y + 1] != '-' && pathMap[(int)point.x][(int)point.y + 1] == 999) {
                pathMap[(int)point.x][(int)point.y + 1] = count;
                queue.add(new Vector2(point.x, point.y + 1));
            }

        }
        return pathMap;
    }

    /**
     * Checks if coordinates are within the bounds of the map
     * @param x coordinate
     * @param y cooridinate
     * @return True if coordinates are in map
     */

    public boolean inBounds(int x, int y) {
        if(x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        else {
            return true;
        }
    }
}