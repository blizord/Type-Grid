package com.mygdx.game;

import java.util.HashMap;
import java.util.Map;

public class EnemyList {
    
    private Map<String, int[][]> levels = new HashMap<String, int[][]>();
    private int[][] first = {{0, 50, 100, 50, 50, 300},
                             {0, 250, 150, 50, 30}};
    
     private int[][] second = {{0, 50, 100, 50, 50, 300},
                               {0, 250, 150, 50, 30}};

    public EnemyList() {
        levels.put("first", first);
        levels.put("second", second);
    }

    public int[][] getLevel(String name) {
        return levels.get(name);
    }
}