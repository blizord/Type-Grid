package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class TowerGenerator {

    private static int towerNum = 3;
    
    public static ArrayList<Tower> getTowers() {
        ArrayList<Tower> towers = new ArrayList<Tower>();
        for(int i = 0; i < towerNum; i++) {
            int direction = (int)(Math.random() * 4);
            int w = Gdx.graphics.getWidth();
            int h = Gdx.graphics.getHeight();
            towers.add(new DamageTower(i * 100 + (w / 2) - (towerNum * 50), (h / 2), direction));
        }
        return towers;
    }
}