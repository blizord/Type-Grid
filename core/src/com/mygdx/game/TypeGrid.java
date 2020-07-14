package com.mygdx.game;

import com.badlogic.gdx.Game;

public class TypeGrid extends Game {
	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		screen.dispose();
	}
}
