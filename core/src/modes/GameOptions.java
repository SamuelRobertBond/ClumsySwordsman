package modes;

import com.mygdx.game.MagnetGame;

public class GameOptions {

	public int rounds = 5;
	public int kills = 5;
	public int seconds = 30;
	public MagnetGame game;
	
	public GameOptions(MagnetGame game) {
		this.game = game;
	}
	
}
