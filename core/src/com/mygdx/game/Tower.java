package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract class Tower {
    private String[] keyData = {"A","B","C","D","E"};
    private int size;
    private Color color;
    private String key;
    private int cost;
    private float waitTime;
    private float wait = 0;
    private Vector2 position;
    private Rectangle area;
    private boolean holding = false;
    private boolean placed = false;

    public Tower(int size, Color color, int x, int y, int waitTime, int cost) {
        this.size = size;
        this.color = color;
        this.position = new Vector2(x, y);
        this.area = new Rectangle(x, y, size, size);
        this.waitTime = waitTime;
        this.cost = cost;
        setKey();
    }

    abstract void draw(ShapeRenderer shape);

    abstract void drawString(SpriteBatch batch, BitmapFont font);

    //abstract void rotate();

    public void setKey() {
        int i = (int)(Math.random() * keyData.length);
        key = keyData[i];
    }

    public void resetWait() {
        wait = waitTime;
    }

    public void tickWait(float delta) {
        wait = wait - delta;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public float getWait() {
        return wait;
    }

    public String getKey() {
        return key;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getArea() {
        return area;
    }

    public boolean isHolding() {
        return holding;
    }

    public void setHolding() {
        holding = true;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void holdingUpdate(int x, int y) {
        position.x = (int)x/16*16 - (size / 2) + 8;
        position.y = (int)y/16*16 - (size / 2) + 8;
        area.x = position.x;
        area.y = position.y;
    }

    public void place() {
        holding = false;
        placed = true;
    }

    public void drawCost(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "$" + cost, position.x, position.y + size + 20);
    }

    public int getCost() {
        return cost;
    }
}