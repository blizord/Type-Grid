package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TypeGrid extends Game {
	public SpriteBatch batch;
	public ShapeRenderer shape;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
