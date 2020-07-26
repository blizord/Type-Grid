package com.mygdx.game;

import com.badlogic.gdx.Game;

public class TypeGrid extends Game {

	private Game game;
	
	@Override
	public void create () {
		game = this;
		setScreen(new SelectScreen(game));
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
