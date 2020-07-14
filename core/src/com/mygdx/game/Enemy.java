package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

    private int size = 8;
    private Color color = new Color(1, 0, 0, 1);
    private int speed = 1;
    private Vector2 position;
    private int[][] path;
    
    public Enemy() {
    }

    public void setPosition(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPath(int[][] p) {
        path = p;
    }

    public int[][] getPath() {
        return path;
    }
}