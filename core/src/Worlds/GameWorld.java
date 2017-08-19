package Worlds;

import java.util.LinkedList;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import Entities.Player;
import Systems.CollisionSystem;
import Systems.GameModeSystem;
import Systems.InputSystem;
import Systems.MapRenderingSystem;
import Systems.PhysicsSystem;
import Systems.RenderSystem;
import modes.GameMode;
import utils.Constants;
import utils.MapUtils;
import utils.SpawnInformation;

public class GameWorld {

	private Engine engine;
	private LinkedList<Player> players;
	private SpawnInformation info[] = Constants.spawns;
	
	//Systems with disposables
	private MapRenderingSystem mapRenderSystem;
	private RenderSystem renderSystem;
	private GameModeSystem modeSystem;
	private InputSystem inputSystem;
	
	/**
	 * Handles the setup and updating of gameplay functions
	 * @param mode - The specifications for the game mode
	 * @param camera - camera used to proejct the game
	 * @param map - Tiled Map used in the game
	 */
	public GameWorld(GameMode mode, OrthographicCamera camera, TiledMap map) {
		
		engine = new Engine();
		
		inputSystem = new InputSystem();
		
		//Physics Systems (disposed when the PhysicsWorld.world is destroyed)
		engine.addSystem(new PhysicsSystem());
		engine.addSystem(inputSystem);
		engine.addSystem(new CollisionSystem());
		
		//Rendering Systems
		mapRenderSystem = new MapRenderingSystem(camera, MapUtils.createMap(map));
		renderSystem = new RenderSystem(camera);
		
		engine.addSystem(mapRenderSystem);
		engine.addSystem(renderSystem);
		
		//Game Systems
		modeSystem = new GameModeSystem(mode);
		engine.addSystem(modeSystem);
		
		//Debug Systems
		//engine.addSystem(new DebugRenderSystem(camera));
		
		players = new LinkedList<Player>();

		
	}
	
	public void addPlayers(LinkedList<Controller> controllers){
		
		//Add Entities
		for(int i = 0; i < Controllers.getControllers().size; ++i){
			players.add(new Player(info[i].x, info[i].y, info[i].rotation, info[i].texture, info[i].color, controllers.get(i)));
			engine.addEntity(players.get(i));
			Gdx.app.log("GameWorld", "Added Player");
		}
		
	}
	
	public Player addPlayer(Controller controller, int i){
		players.add(new Player(info[i].x, info[i].y, info[i].rotation, info[i].texture, info[i].color, controller));
		engine.addEntity(players.get(i));
		return players.get(i);
	}
	
	public void update(float delta){
		engine.update(delta);
	}
	
	public void dispose(){
		
		PhysicsWorld.world.dispose();
		for(Player player : players){
			player.dispose();
		}
		mapRenderSystem.dispose();
		renderSystem.dispose();
		modeSystem.dispose();
		inputSystem.dispose();
	}
	
}
