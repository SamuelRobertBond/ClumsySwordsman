package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;

import Worlds.PhysicsWorld;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class CollisionUtils {

	/*
	 * @param x - starting x position of the body
	 * y - starting y position of the body
	 * world - the physics world to use
	 */
	
	/**
	 * 
	 * @param x - starting xpos
	 * @param y - starting ypos
	 * @param world - physics world which the shape shall belong to
	 * @param type - Type of Body (Use BodyType class)
	 * @return
	 */
	public static Body defineBody(float x, float y, float linearDampening, BodyType type){
		
		BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = type;
		bodyDef.position.set(x, y);
		bodyDef.linearDamping = linearDampening;
		
		Gdx.app.log("Collision Utils", "Body Created");
		
		return PhysicsWorld.world.createBody(bodyDef);
		
	}
	
	
	/**
	 * Defines a rectangular fixture for a a specified body
	 * @param width 
	 * @param height
	 * @param density - weight
	 * @param restitution - bounce
	 * @param body
	 * @return
	 */
	public static Fixture defineRectangularFixture(float width, float height, float density, float restitution,boolean isSensor, Body body){
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = density;
		fixDef.friction = 1;
		fixDef.restitution = restitution;
		fixDef.isSensor = isSensor;
		
		Fixture fixture = body.createFixture(fixDef);
		shape.dispose();
		
		return fixture;
	}
	
	
	
	/**
	 * Defines a circular fixture for a a specified body
	 * @param density - defines the density of the fixture
	 * @param raduis - defines the size of the fixture
	 * @param body - body that the fixture will be applied to
	 * @return
	 */
	public static Fixture defineCircularFixture(float density, float radius, boolean isSensor, Body body){
		
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = density;
		fixDef.friction = 1;
		fixDef.isSensor = isSensor;
		
		Fixture fixture = body.createFixture(fixDef);
		shape.dispose();
		
		Gdx.app.log("Collision Utils", "Circular Fixture Created");
		
		return fixture;
	}
	
	
	public static RevoluteJoint createRevoluteJoint(Body a, Body b, float rotationSpeed){
		
		RevoluteJointDef jointDef = new RevoluteJointDef();
		
		jointDef.bodyA = a;
		jointDef.bodyB = b;
		
		
		jointDef.collideConnected = false;
		jointDef.enableMotor = false;
		
		jointDef.maxMotorTorque = 0;
		jointDef.motorSpeed = 0;

		
		return (RevoluteJoint)(PhysicsWorld.world.createJoint(jointDef));
		
	}
	
	public static WeldJoint createWeldJoint(float distance, Body a, Body b){
		
		WeldJointDef jointDef = new WeldJointDef();
		jointDef.bodyA = a;
		jointDef.bodyB = b;
		jointDef.collideConnected = false;
		
		jointDef.frequencyHz = 60;
		
		jointDef.localAnchorA.set(a.getLocalCenter().x + distance, 0);
		
		return (WeldJoint)PhysicsWorld.world.createJoint(jointDef);
	}
	
}
