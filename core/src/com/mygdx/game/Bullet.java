package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    
    private float speed = 60;
    private int direction;
    private Rectangle area;
    //private Vector2 position;
    private Color color = new Color(1, 1, 1, 1);
    private int size = 4;

    public Bullet(int dir, Vector2 pos) {
        direction = dir;
        //position = pos;
        area = new Rectangle(pos.x, pos.y, size, size);
    }

    public Color getColor() {
        return color;
    }

    public Rectangle getArea() {
        return area;
    }

    //public Vector2 getPosition() {
    //   return position;
    //}

    public int getSize() {
        return size;
    }

    public void update(float delta) {
        switch(direction) {
            case 0:
                area.y = area.y - speed * delta;
                break;
            case 1:
                area.x = area.x + speed * delta;
                break;
            case 2:
                area.y = area.y + speed * delta;
                break;
            case 3:
                area.x = area.x - speed * delta;
                break;
        }
    }

    public void draw() {

    }

}