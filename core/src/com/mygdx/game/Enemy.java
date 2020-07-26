package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

    private int size = 8;
    private Color color = new Color(1, 0, 0, 1);
    private float speed = (float).125 * 25;
    private Vector2 position;
    private Rectangle area = new Rectangle();
    private int direction;
    private int[][] path;
    
    public Enemy() {
    }

    public void setPosition(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getArea(int tileSize) {;
        area.x = position.x * tileSize;
        area.y = position.y * tileSize;
        area.width = size;
        area.height = size;
        return area;
    }

    public void setDirection(int d) {
        direction = d;
    }

    public int getDirection() {
        return direction;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public float getSpeed() {
        return speed;
    }

    public void setPath(int[][] p) {
        path = p;
    }

    public int[][] getPath() {
        return path;
    }
}