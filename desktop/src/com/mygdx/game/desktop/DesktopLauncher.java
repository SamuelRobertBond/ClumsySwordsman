package com.mygdx.game.desktop;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.MagnetGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		
		DisplayMode mode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		config.setFullscreenMode(mode);
		config.setWindowedMode(600, 600);
		config.setTitle("Clumsy Swordsman");

		new Lwjgl3Application(new MagnetGame(), config);
	}
}
