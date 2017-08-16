package Worlds;

import java.util.LinkedList;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import Entities.Player;
import Systems.CollisionSystem;
import Systems.DebugRenderSystem;
import Systems.GameModeSystem;
import Systems.InputSystem;
import Systems.MapRenderingSystem;
import Systems.PhysicsSystem;
import Systems.RenderSystem;
import modes.GameOptions;
import modes.LastManStanding;
import utils.Constants;
import utils.MapUtils;
import utils.SpawnInformation;

public class GameWorld {

	private Engine engine;
	private LinkedList<Player> players;
	
	public GameWorld(OrthographicCamera camera) {
		
		engine = new Engine();
		
		//Physics Systems
		engine.addSystem(new PhysicsSystem());
		engine.addSystem(new InputSystem());
		engine.addSystem(new CollisionSystem());

		//Rendering Systems
		engine.addSystem(new MapRenderingSystem(camera, MapUtils.createMap(Constants.MAP)));
		engine.addSystem(new RenderSystem(camera));
		
		//Game Systems
		engine.addSystem(new GameModeSystem(new LastManStanding(new GameOptions())));
		
		//Debug Systems
		engine.addSystem(new DebugRenderSystem(camera));
		
		players = new LinkedList<Player>();
		
		//Get Spawn Information
		SpawnInformation info[] = Constants.spawns;
		
		//Add Entities
		for(int i = 0; i < Controllers.getControllers().size; ++i){
			players.add(new Player(info[i].x, info[i].y, info[i].rotation, info[i].texture, info[i].color, Controllers.getControllers().get(i)));
			engine.addEntity(players.get(i));
			Gdx.app.log("GameWorld", "Added Player");
		}
		
	}
	
	public void update(float delta){
		engine.update(delta);
	}
	
}
