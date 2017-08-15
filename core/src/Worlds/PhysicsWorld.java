package Worlds;

import java.util.HashMap;
import java.util.Stack;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

import utils.Constants;

public class PhysicsWorld {

	public static World world;
	private static Stack<Joint> joints;
	private static Stack<Body> bodies;
	private static Stack<Body> fixtureKeys;
	private static HashMap<Body, Fixture> fixtures;
	
	public static World setWorld(World world){
		PhysicsWorld.world = world;
		joints = new Stack<Joint>();
		bodies = new Stack<Body>();
		fixtures = new HashMap<Body, Fixture>();
		fixtureKeys = new Stack<Body>();
		return world;
	}
	
	public static void queueJointDestory(Joint joint){
		joints.push(joint);
	}
	
	public static void queueBodyDestory(Body body){
		bodies.push(body);
	}
	
	public static void step(){
		world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
		
		while(!joints.isEmpty()){
			world.destroyJoint(joints.pop());
		}
		
		while(!fixtureKeys.isEmpty()){
			Body body = fixtureKeys.pop();
			Fixture fixture = fixtures.get(body);
			fixtures.remove(body);
			
			body.destroyFixture(fixture);
		}
		
		while(!bodies.isEmpty()){
			world.destroyBody(bodies.pop());
		}
	}

	public static void queueFixtureDestory(Body body, Fixture fixture) {
		fixtures.put(body, fixture);
		fixtureKeys.push(body);
		
	}
}
