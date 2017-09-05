package modes;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.MagnetGame;

import Components.ScoreComponent;
import Components.VelocityComponent;
import Entities.Player;
import screens.SelectScreen;
import utils.Constants;

public class GameMode{

	protected GameOptions options;
	protected int highestScore;
	
	protected ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	protected ComponentMapper<ScoreComponent> sm = ComponentMapper.getFor(ScoreComponent.class);
	
	private MagnetGame game;
	
	public GameMode(MagnetGame game, GameOptions options) {
		this.game = game;
		this.options = options;
		this.highestScore = 0;
	}
	
	
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

	public void reset(ImmutableArray<Entity> entities) {
		Gdx.app.log("Last Man Standing", "Reseting");
		for(int i = 0; i < entities.size(); ++i){
			Player p = (Player)entities.get(i);
			p.reset();
		}
	}

	public void addScore(ImmutableArray<Entity> entities) {
		//Overridden in children
	}


	public boolean endGame() {
		
		if(highestScore >= options.score){
			
			Gdx.app.log("Game Mode - endGame()", "Ending Game");
			
			Timer timer = new Timer();
			timer.scheduleTask(new Task(){

				@Override
				public void run() {;
					game.getScreen().dispose();
					game.setScreen(new SelectScreen(game));
				}
				
			}, 4);
			
			
			return true;
		}
		return false;
	}

}
