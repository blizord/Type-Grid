package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class World {
    
    private GameMap map = new GameMap("first");
    private ArrayList<Enemy> Enemies = new ArrayList<Enemy>();


    public World() {
    }

    public void drawMap(ShapeRenderer shape) {
        map.draw(shape);
    }

    public void testDrawMap(BitmapFont font, SpriteBatch batch) {
        map.testDraw(font, batch);
    }

    public void addEnemy() {
        Enemy e = new Enemy();
        e.setPosition(map.getStart());
        e.setPath(map.pathFind((int)e.getPosition().x, (int)e.getPosition().y, (int)map.getEnd().x, (int)map.getEnd().y));
        Enemies.add(e);
    }

    public void updateEnemies() {
        for(Enemy e : Enemies) {
            int[][] path = e.getPath();
        }
    }
}