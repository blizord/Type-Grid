package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DamageTower extends Tower {

    private int direction = 0;

    public DamageTower(int x, int y, int dir) {
        super(32, new Color(1, 1, 1, 1), x, y, 3, 100);
        direction = dir;
    }

    public void rotate() {
        direction++;
        if(direction >= 4) {
            direction = 0;
        }
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        int size = getSize();
        int x = (int)getPosition().x;
        int y = (int)getPosition().y;
        shape.setColor(getColor());
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

    @Override
    public void drawString(SpriteBatch batch, BitmapFont font) {
        int size = getSize();
        int x = (int)getPosition().x;
        int y = (int)getPosition().y;
        switch(direction) {
            case 0:
                font.draw(batch, getKey(), x + (size / 3), y + (size / 2));
                break;
            case 1:
                font.draw(batch, getKey(), x + (size / 4), y + (size / 3));
                break;
            case 2:
                font.draw(batch, getKey(), x + (size / 3), y + (size / 4));
                break;
            case 3:
                font.draw(batch, getKey(), x + (size / 2), y + (size / 4));
                break;
        }
    }
}