package com.mygdx.game;


import com.badlogic.gdx.Game;

import screens.GameScreen;
import screens.MainMenuScreen;
import utils.Constants;

public class MagnetGame extends Game{

	@Override
	public void create() {
		Constants.loadMaps();
		setScreen(new MainMenuScreen(this));
		
	}

	
	
}
