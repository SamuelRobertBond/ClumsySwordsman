package modes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.MagnetGame;

public class SelectMode extends GameMode{

	public SelectMode(MagnetGame game, GameOptions options) {
		super(game, options);
	}
	
	@Override
	public boolean endRound(ImmutableArray<Entity> entities) {
		return false;
	}

}
