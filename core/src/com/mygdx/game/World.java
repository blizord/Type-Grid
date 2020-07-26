package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {
    
    private GameMap map;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Tower> towers = new ArrayList<Tower>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private EnemyList enemyData = new EnemyList();
    private int health = 5;
    private int money = 200;
    private int level = -1;
    private int[][] enemyLevel;
    private int enemyCount = 0;
    private int enemyWait;
    private int enemiesGone;
    private boolean enemiesLeft = true;
    private boolean inLevel = false;
    private boolean buying = false;

    public World(String levelName) {
        enemyLevel = enemyData.getLevel(levelName);
        map = new GameMap(levelName);
        enemyWait = enemyLevel[0][enemyCount];
        resetTowerBuy();
    }

    public void resetTowerBuy() {
        ArrayList<Tower> toRemove = new ArrayList<Tower>();
        for(Tower t : towers) {
            if(!t.isPlaced()) {
                toRemove.add(t);
            }
        }
        for(Tower t : toRemove) {
            towers.remove(t);
        }
        ArrayList<Tower> towerList = TowerGenerator.getTowers();
        for(Tower t : towerList) {
            towers.add(t);
        }
    }

    public void drawMap(ShapeRenderer shape) {
        map.draw(shape);
    }

    public boolean inLevel() {
        return inLevel;
    }

    public boolean isBuying() {
        return buying;
    }

    public void setBuying() {
        buying = !buying;
    }

    public void nextLevel() {
        level++;
        inLevel = true;
        enemiesLeft = true;
        enemyWait = enemyLevel[level][enemyCount];
        resetTowerBuy();
    }

    public void testDrawMap(BitmapFont font, SpriteBatch batch) {
        map.testDraw(font, batch);
    }

    public void addEnemy() {
        Enemy e = new Enemy();
        ArrayList<Vector2> starts = map.getStarts();
        int r = (int)(Math.random() * starts.size());
        e.setPosition(starts.get(r));
        e.setPath(map.pathFind((int)e.getPosition().x, (int)e.getPosition().y, (int)map.getEnd().x, (int)map.getEnd().y));
        enemies.add(e);
    }

    public void removeEnemy(Enemy e) {
        enemies.remove(e);
        enemiesGone++;
        if(enemiesGone == enemyLevel[level].length) {
            inLevel = false;
            enemiesGone = 0;
            enemyCount = 0;
        }
    }

    public void updateTowers(float delta) {
        for(Tower t : towers) {
            t.tickWait(delta);
        }
    }

    public void updateBullets(float delta) {
        ArrayList<Bullet> toRemove = new ArrayList<Bullet>();
        Enemy enemyToRemove = null;
        for(Bullet b : bullets) {
            b.update(delta);
            for(Enemy e : enemies) {
                Rectangle eArea = e.getArea(map.getTileSize());
                Rectangle bArea = b.getArea();
                //Vector2 ePos = new Vector2(e.getPosition().x * map.getTileSize() + e.getSize() / 2, e.getPosition().y * map.getTileSize() + e.getSize() / 2);
                //Vector2 bPos = new Vector2(b.getPosition().x + b.getSize() / 2, b.getPosition().y + b.getSize() / 2);
                //double distance = Math.sqrt(Math.pow(bPos.x - ePos.x, 2) + Math.pow(bPos.y - ePos.y, 2));
                //if(distance <= e.getSize()) {
                //    toRemove.add(b);
                //    enemyToRemove = e;
                //}
                if(eArea.overlaps(bArea)) {
                    toRemove.add(b);
                    enemyToRemove = e;
                }
            }
            if(!map.inBounds((int)(b.getArea().x / map.getTileSize()), (int)(b.getArea().y / map.getTileSize()))) {
                toRemove.add(b);
            }
        }
        for(Bullet b : toRemove) {
            bullets.remove(b);
        }
        if(enemyToRemove != null) {
            removeEnemy(enemyToRemove);
        }
    }

    public void drawTowers(ShapeRenderer shape) {
        for(Tower t : towers) {
            if(!buying && (t.isHolding() || t.isPlaced())) {
                t.draw(shape);
            }
            else if(buying && !t.isPlaced()) {
                t.draw(shape);
            }
        }
    }

    public void drawTowersStrings(SpriteBatch batch, BitmapFont font) {
        for(Tower t : towers) {
            if(t.getWait() > 0) {
                continue;
            }
            if(!buying && (t.isHolding() || t.isPlaced())) {
                t.drawString(batch, font);
            }
            else if(buying && !t.isPlaced()) {
                t.drawString(batch, font);
            }
            if(buying && !t.isPlaced() && !t.isHolding()) {
                t.drawCost(batch, font);
            }
        }
    }

    public void drawHealth(SpriteBatch batch, BitmapFont font) {
        if(buying) {
            return;
        }
        Vector2 pos = map.getEnd();
        int tileSize = map.getTileSize();
        font.draw(batch, "" + health, pos.x * tileSize + 4, pos.y * tileSize + 4);
    }

    public void drawMoney(SpriteBatch batch, BitmapFont font) {
        if(!buying) {
            return;
        }
        font.draw(batch, "Money: $" + money, 10, 10);
    }

    public void rotateTower() {
        for(Tower t : towers) {
            if(t.isHolding()) {
                ((DamageTower) t).rotate();
            }
        }
    }

    public void placeGrabTower(int x, int y) {
        for(Tower t : towers) {
            if(t.isHolding()) {
                Vector2 pos = t.getPosition();
                if(map.open((int)(pos.x / map.getTileSize()), (int)(pos.y / map.getTileSize())) && map.open((int)(pos.x / map.getTileSize() + 2), (int)(pos.y / map.getTileSize() + 2)) && map.open((int)(pos.x / map.getTileSize() + 2), (int)(pos.y / map.getTileSize())) && map.open((int)(pos.x / map.getTileSize()), (int)(pos.y / map.getTileSize() + 2)) && map.open((int)(pos.x / map.getTileSize() + .5), (int)(pos.y / map.getTileSize() + .5))) {
                    for(Tower t2 : towers) {
                        Vector2 pos2 = t2.getPosition();
                        if(t == t2) {
                            continue;
                        }
                        if(pos.x >= pos2.x - t2.getSize() / 2 && pos.x <= pos2.x + t2.getSize() / 2 && pos.y >= pos2.y - t2.getSize() / 2 && pos.y <= pos2.y + t2.getSize() / 2) {
                            return;
                        }
                    }
                    t.place();
                    return;
                }
            }
        }
        for(Tower t : towers) {
            if(towerClicked(t, x, y) && !t.isHolding() && !t.isPlaced() && buying && money >= t.getCost()) {
                t.setHolding();
                money -= t.getCost();
                buying = false;
            }
        }
    }

    private boolean towerClicked(Tower t, int x, int y) {
        if(t.getArea().contains((float)x, (float)y)) {
            return true;
        }
        return false;
    }

    public boolean isHoldingTower() {
        for(Tower t : towers) {
            if(t.isHolding()) {
                return true;
            }
        }
        return false;
    }

    public void holdingUpdateTower(int x, int y) {
        for(Tower t : towers) {
            if(t.isHolding()) {
                t.holdingUpdate(x, y);
            }
        }
    }

    public int[] getTowerKeys() {
        ArrayList<String> towerKeys = new ArrayList<String>();
        for(Tower t : towers) {
            if(!towerKeys.contains(t.getKey()));
            towerKeys.add(t.getKey());
        }
        int[] keyCodes = new int[towerKeys.size()];
        for(int i = 0; i < towerKeys.size(); i++) {
            keyCodes[i] = Input.Keys.valueOf(towerKeys.get(i));
        }
        return keyCodes;
    }

    public void shootTower(int key) {
        for(Tower t : towers) {
            if(Input.Keys.toString(key) == t.getKey() && t.isPlaced()) {
                if(t.getWait() > 0) {
                    t.resetWait();
                }
                else{
                    float add = (t.getSize() / 2) - 2;
                    bullets.add(new Bullet(((DamageTower)t).getDirection(), new Vector2(t.getPosition().x + add, t.getPosition().y + add)));
                    t.resetWait();
                }
            }
        }
    }

    public void updateEnemies(float delta, Game game) {
        if(!inLevel) {
            return;
        }
        enemyWait--;
        if(enemyWait <= 0 && enemiesLeft) {
            addEnemy();
            enemyCount++;
            if(enemyCount >= enemyLevel[level].length) {
                enemiesLeft = false;
            }
            else {
                enemyWait = enemyLevel[level][enemyCount];
            }
        }
        ArrayList<Enemy> toRemove = new ArrayList<Enemy>();
        for(Enemy e : enemies) {
            int[][] path = e.getPath();
            if(e.getPosition().x % 1 > .08 || e.getPosition().y % 1 > .08) {
                switch(e.getDirection()) {
                    case 0:
                        e.setPosition(new Vector2(e.getPosition().x, (float)(e.getPosition().y - e.getSpeed() * delta)));
                        break;
                    case 1:
                        e.setPosition(new Vector2(e.getPosition().x, (float)(e.getPosition().y + e.getSpeed() * delta)));
                        break;
                    case 2:
                        e.setPosition(new Vector2((float)(e.getPosition().x - e.getSpeed() * delta), e.getPosition().y));
                        break;
                    case 3:
                        e.setPosition(new Vector2((float)(e.getPosition().x + e.getSpeed() * delta), e.getPosition().y));
                        break;
                }
                continue;
            }
            if(path[(int)e.getPosition().x][(int)e.getPosition().y] == 0) {
                toRemove.add(e);
                health--;
                if(health <= 0) {
                    lose(game);
                }
            } 
            if(map.inBounds((int)e.getPosition().x + 1, (int)e.getPosition().y) && path[(int)e.getPosition().x + 1][(int)e.getPosition().y] < path[(int)e.getPosition().x][(int)e.getPosition().y]) {
                e.setPosition(new Vector2((float)(e.getPosition().x + e.getSpeed() * delta), e.getPosition().y));
                e.setDirection(3);
            }
            else if(map.inBounds((int)e.getPosition().x - 1, (int)e.getPosition().y) && path[(int)e.getPosition().x - 1][(int)e.getPosition().y] < path[(int)e.getPosition().x][(int)e.getPosition().y]) {
                e.setPosition(new Vector2((float)(e.getPosition().x - e.getSpeed() * delta), e.getPosition().y));
                e.setDirection(2);
            }
            else if(map.inBounds((int)e.getPosition().x, (int)e.getPosition().y + 1) && path[(int)e.getPosition().x][(int)e.getPosition().y + 1] < path[(int)e.getPosition().x][(int)e.getPosition().y]) {
                e.setPosition(new Vector2(e.getPosition().x, (float)(e.getPosition().y + e.getSpeed() * delta)));
                e.setDirection(1);
            }
            else if(map.inBounds((int)e.getPosition().x, (int)e.getPosition().y - 1) && path[(int)e.getPosition().x][(int)e.getPosition().y - 1] < path[(int)e.getPosition().x][(int)e.getPosition().y]) {
                e.setPosition(new Vector2(e.getPosition().x, (float)(e.getPosition().y - e.getSpeed() * delta)));
                e.setDirection(0);
            }
        }
        for(Enemy e : toRemove) {
            removeEnemy(e);
        }
    }

    public void lose(Game game) {
        System.out.println("YOU LOSE");
        game.setScreen(new SelectScreen(game));
    }

    public void drawEnemies(ShapeRenderer shape) {
        for(Enemy e : enemies) {
            shape.setColor(e.getColor());
            shape.rect(e.getPosition().x * map.getTileSize() + (e.getSize() / 2), e.getPosition().y * map.getTileSize() + (e.getSize() / 2), e.getSize(), e.getSize());
        }
    }

    public void drawBullets(ShapeRenderer shape) {
        for(Bullet b : bullets) {
            shape.setColor(b.getColor());
            Rectangle area = b.getArea();
            shape.rect(area.x, area.y, area.width, area.height);
        }
    }
}