package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {

    private World world;

    private Game game;
    private SpriteBatch batch = new SpriteBatch();
	private  ShapeRenderer shape = new ShapeRenderer();
	private BitmapFont font = new BitmapFont(true);
    private OrthographicCamera camera = new OrthographicCamera();

    private Vector3 touchPos;

    public GameScreen(Game game, String level) {
        this.game = game;
        world = new World(level);
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(float deltaTime) {
        if (Gdx.input.justTouched()) { // Mouse Pressed
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            world.placeGrabTower((int)touchPos.x, (int)touchPos.y);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !world.inLevel() && !world.isBuying()){ // Space pressed 62
            world.nextLevel();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {  // 'R' Pressed 46
            world.rotateTower();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.O) && !world.inLevel() && !world.isHoldingTower()){  // 'O' Pressed 43
            world.setBuying();
        }

        int[] towerKeys = world.getTowerKeys();
        for(int key : towerKeys) {
            if (Gdx.input.isKeyJustPressed(key)) {
                world.shootTower(key);
            }
        }

        if(world.isHoldingTower()) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            world.holdingUpdateTower((int)touchPos.x, (int)touchPos.y);
        }

        world.updateTowers(deltaTime);
        world.updateEnemies(deltaTime, game);
        world.updateBullets(deltaTime);
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Shape drawing

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        if(!world.isBuying()) {
            world.drawMap(shape);
        }
        world.drawTowers(shape);
        shape.end();

        shape.begin(ShapeType.Filled);
        world.drawEnemies(shape);
        world.drawBullets(shape);
        shape.end();

        // Sprite drawing

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.drawTowersStrings(batch, font);
        world.drawHealth(batch, font);
        world.drawMoney(batch, font);
        batch.end();

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
		shape.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height, false);
    }
}