package com.mygdx.game;


import com.badlogic.gdx.Game;

import screens.GameScreen;

public class MagnetGame extends Game{

	@Override
	public void create() {
		
		setScreen(new GameScreen(this));
		
	}

	
	
}
