package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DamageTower extends Tower {

    private int damage = 5;
    private int direction = 0;

    public DamageTower() {
        super(32, new Color(1, 1, 1, 1));
    }

    @Override
    public void draw(ShapeRenderer shape, int x, int y) {
        shape.setColor(color);
        switch(direction) {
            case 0:
                shape.line(x, y + size, x + (size / 2), y);
                shape.line(x + (size / 2), y, x + size, y + size);
                shape.line(x, y + size, x + size, y + size);
                break;
            case 1:
                shape.line(x, y, x + size, y + (size / 2));
                shape.line(x, y, x, y + size);
                shape.line(x, y + size, x + size, y + (size / 2));
                break;
            case 2:
                shape.line(x, y, x + size, y);
                shape.line(x, y, x + (size / 2), y + size);
                shape.line(x + (size / 2), y + size, x + size, y);
                break;
            case 3:
                shape.line(x + size, y, x + size, y + size);
                shape.line(x + size, y, x, y + (size / 2));
                shape.line(x, y + (size / 2), x + size, y + size);
                break;
        }
    }
}