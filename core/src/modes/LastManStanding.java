package modes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.MagnetGame;

import Components.ScoreComponent;
import Components.VelocityComponent;

public class LastManStanding extends GameMode{
	

	public LastManStanding(MagnetGame game, GameOptions options) {
		super(game, options);
	}
	
	@Override
	public void addScore(ImmutableArray<Entity> entities) {
		for(int i = 0; i < entities.size(); ++i){
			VelocityComponent vc = vm.get(entities.get(i));
			ScoreComponent sc = sm.get(entities.get(i));
			if(vc.alive){
				++sc.score;
				if(sc.score > highestScore){
					highestScore = sc.score;
				}
			}
		}
	}

}
