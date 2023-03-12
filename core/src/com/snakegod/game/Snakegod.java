package com.snakegod.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class Snakegod extends Game {


	MenuScreen menuScreen;
	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		setMenuScreen();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.render();
	}

	@Override
	public void dispose () {

	}

	public void changeScreen(Screen newScreen) {
		Screen oldScreen = getScreen();
		setScreen(newScreen);
		if(oldScreen != null)
			oldScreen.dispose();
	}

	public void setMenuScreen() {
		setScreen(menuScreen);
	}

	enum direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}