package Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import Components.ScoreComponent;
import Components.VelocityComponent;
import modes.GameMode;
import modes.GameOptions;

public class GameModeSystem extends EntitySystem{

	private boolean roundEnding;
	private GameMode mode;
	private ImmutableArray<Entity> entities;
	private final float RESET_DELAY = 3f;
	
	//Add game options as well
	public GameModeSystem(GameMode mode) {
		this.mode = mode;
		this.roundEnding = false;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(ScoreComponent.class, VelocityComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		

		//Checks if the conditions for ending rounds has been met
		if(mode.endRound(entities) && !roundEnding){
			
			roundEnding = true;
			
			//Assess scores
			mode.addScore(entities);
			
			//Check if the game should end
			if(mode.endGame()){
				
				//Takes care of screen switching in the game mode
				
			}
			//If it shouldn't end queue to reset the players
			else{
				
				Gdx.app.log("Game Mode System", "Setting reset timer");
				
				for(int i = 0; i < entities.size(); ++i){
					Timer timer = new Timer();
					timer.scheduleTask(new Task(){

						@Override
						public void run() {
							mode.reset(entities);
							roundEnding = false;
						}
						
					}, RESET_DELAY);
				}
			}
			
		}
		
	}
	
}
