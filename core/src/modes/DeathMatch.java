package modes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.MagnetGame;

import Components.ScoreComponent;

public class DeathMatch extends GameMode{

	public DeathMatch(MagnetGame game, GameOptions options) {
		super(game, options);
	}
	
	@Override
	public void addScore(ImmutableArray<Entity> entities) {
		for(int i = 0; i < entities.size(); ++i){
			ScoreComponent sc = sm.get(entities.get(i));
			sc.score = sc.kills;
			if(sc.kills > highestScore){
				highestScore = sc.kills;
			}
		}
	}

}
