package modes;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.MagnetGame;

import Components.ScoreComponent;
import Components.VelocityComponent;
import Entities.Player;
import screens.SelectScreen;

public class LastManStanding implements GameMode{

	private GameOptions options;
	private int highestScore;
	
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<ScoreComponent> sm = ComponentMapper.getFor(ScoreComponent.class);
	
	private MagnetGame game;
	
	public LastManStanding(MagnetGame game, GameOptions options) {
		this.game = game;
		this.options = options;
	}
	
	
	@Override
	public boolean endRound(ImmutableArray<Entity> entities) {
		
		//Checks how many players are alive
		int aliveCount = 0;
		for(int i = 0; i < entities.size(); ++i){
			VelocityComponent vc = vm.get(entities.get(i));
			if(vc.alive){
				++aliveCount;
			}
		}
		
		if(aliveCount <= 1){
			return true;
		}
		
		return false;
	}

	@Override
	public void reset(ImmutableArray<Entity> entities) {
		Gdx.app.log("Last Man Standing", "Reseting");
		for(int i = 0; i < entities.size(); ++i){
			Player p = (Player)entities.get(i);
			p.reset();
		}
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


	@Override
	public boolean endGame() {
		if(highestScore >= options.rounds){
			
			Timer timer = new Timer();
			timer.scheduleTask(new Task(){

				@Override
				public void run() {
					game.getScreen().dispose();
					options.game.setScreen(new SelectScreen(options.game));
				}
				
			}, 4);
			
			
			return true;
		}
		return false;
	}

}
