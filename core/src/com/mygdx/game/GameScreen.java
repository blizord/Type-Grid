package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends ScreenAdapter {

    private World world = new World();

    private SpriteBatch batch = new SpriteBatch();
	private  ShapeRenderer shape = new ShapeRenderer();
	private BitmapFont font = new BitmapFont(true);
    private OrthographicCamera camera = new OrthographicCamera();

    int testVar = 0;
    Tower testTower = new DamageTower();

    public GameScreen() {
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(float deltaTime) {
        if (Gdx.input.justTouched()) { // Mouse Pressed
            testVar++;
        }
        else if (Gdx.input.isKeyJustPressed(29)) {  // 'A' Pressed
            testVar--;
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Line drawing

        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        world.drawMap(shape);
        testTower.draw(shape, 32, 32);
        shape.end();

        // Sprite drawing

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.testDrawMap(font, batch);
        //font.draw(batch, "Test Variable: " + testVar, 5, 5);
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
}