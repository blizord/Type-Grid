package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SelectScreen extends ScreenAdapter {

    private Game game;
    private OrthographicCamera camera = new OrthographicCamera();
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont(true);

    private int numLevels = 3;
    private int selectedLevel = 0;

    private String[] levelNames = {"first", "second", "third"};
    
    public SelectScreen(Game game) {
        this.game = game;
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedLevel--;
            if(selectedLevel < 0) {
                selectedLevel = numLevels - 1;
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedLevel++;
            if(selectedLevel >= numLevels) {
                selectedLevel = 0;
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game, levelNames[selectedLevel]));
        }
    }

    public void draw() {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Sprite drawing

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(int i = 0; i < numLevels; i++) {
            font.draw(batch, "Level " + (i + 1), 100, i * 50 + 100);
        }
        font.draw(batch, ">", 75, selectedLevel * 50 + 100);
        batch.end();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
    }
}