package com.mygdx.game;


import com.badlogic.gdx.Game;
import screens.MainMenuScreen;

public class MagnetGame extends Game{

	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	}

	
	
}
