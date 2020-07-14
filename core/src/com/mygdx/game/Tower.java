package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

abstract class Tower {
    int size;
    Color color;

    public Tower(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    abstract void draw(ShapeRenderer shape, int x, int y);
}