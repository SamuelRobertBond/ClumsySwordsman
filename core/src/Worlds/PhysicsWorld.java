package Worlds;

import com.badlogic.gdx.physics.box2d.World;

public class PhysicsWorld {

	public static World world;
	
	public static World setWorld(World world){
		PhysicsWorld.world = world;
		return world;
	}
	
}
