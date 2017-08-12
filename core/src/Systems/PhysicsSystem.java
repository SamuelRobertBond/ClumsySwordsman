package Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import Worlds.PhysicsWorld;
import utils.Constants;

public class PhysicsSystem extends EntitySystem{
	
	//Collisions Here
	
	public PhysicsSystem() {
		PhysicsWorld.setWorld(new World(new Vector2(0, 0), false));
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		
		
	}
	
	@Override
	public void update(float deltaTime) {
		PhysicsWorld.world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
	}
	
}
