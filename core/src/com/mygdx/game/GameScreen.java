package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends ScreenAdapter {

    TypeGrid game;
    GameMap map = new GameMap("first");
    ShapeRenderer shape = new ShapeRenderer();

    OrthographicCamera camera;
    BitmapFont font = new BitmapFont(true);

    int testVar = 0;

    public GameScreen(TypeGrid game) {
        this.game = game;

        camera = new OrthographicCamera();
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

        game.shape.setProjectionMatrix(camera.combined);
        game.shape.begin(ShapeType.Line);
        map.draw(game.shape);
        game.shape.end();

        // Sprite drawing

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        font.draw(game.batch, "Test Variable: " + testVar, 5, 5);
        game.batch.end();

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void hide() {
        font.dispose();
    }
}