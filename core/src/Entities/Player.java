package Entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;

import Components.BodyComponent;
import Components.ControllerComponent;
import Components.RotationComponent;
import Components.SpeedComponent;
import Components.SpriteComponent;
import Components.VelocityComponent;
import utils.CollisionUtils;
import utils.Constants;

public class Player extends Entity{
	
	private final float SCALE = 1.4f;
	private final float PLAYER_SPRITE_SCALE = .034f * SCALE;
	private final float SWORD_SPRITE_SCALE_X = .05f * SCALE;
	private final float SWORD_SPRITE_SCALE_Y = .25f * SCALE;
	
	private final float RADIUS = 2f * SCALE;
	private final float SPEED = 100f * SCALE;
	private final float LINEAR_DAMPENING = 4;
	private final float DENSITY = 20f;
	private final float ROTATION_SPEED = 0f;
	
	private final float ROTATION_JOINT_WIDTH = 1 * SCALE;
	private final float ROTATION_JOINT_HEIGHT = 1 * SCALE;
	
	private final float SWORD_WIDTH = 2 * SCALE;
	private final float SWORD_HEIGHT = .3f * SCALE;
	private final float SWORD_DISTANCE = SWORD_WIDTH + RADIUS + (.3f * SCALE);
	private final float SWORD_DENSITY = 3;
	
	
	private final float STAB_SPEED = 30000;
	
	
	/*
	 * This is the actor that the player directly controls
	 * float x - x position
	 * float y - y positionS
	 * Controller controller - Gamepad that controls the player;
	 */
	public Player(float x, float y, float rotation, Texture texture, Controller controller) {
		
		//How It Works
				//Center Body Connects to the Connection Body with a revolute joint to allow rotation between two bodies
				//The Connection Body connects to the sword with a revolute joint, allowing for a stabing motion
		
		//Defines the players center body
		Body centerBody = CollisionUtils.defineBody(x, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture bodyFixture = CollisionUtils.defineCircularFixture(DENSITY, RADIUS, false, centerBody);
		bodyFixture.setUserData(this);
		centerBody.setFixedRotation(true);
		centerBody.resetMassData();
		
		//Defines connection body for the sword and center
		//This is need to apply two different joints
		Body rotationBody = CollisionUtils.defineBody(x, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		CollisionUtils.defineRectangularFixture(ROTATION_JOINT_WIDTH, ROTATION_JOINT_HEIGHT, 1, 0, false, rotationBody);
		rotationBody.setTransform(rotationBody.getPosition(), rotation);
		rotationBody.setAngularDamping(100000);
		
		//Connects the circular bodies using the revolute joint
		RevoluteJoint rotationJoint = CollisionUtils.createRevoluteJoint(centerBody, rotationBody, ROTATION_SPEED);
		
		//Creates the sword body
		Body sword = CollisionUtils.defineBody(x + RADIUS, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture swordFixture = CollisionUtils.defineRectangularFixture(SWORD_WIDTH, SWORD_HEIGHT, SWORD_DENSITY, 0, false, sword);
		swordFixture.setUserData(this);
		
		//Connects the sword body to the rotationBody with a weld joint
		CollisionUtils.createWeldJoint(SWORD_DISTANCE, rotationBody, sword);
		
		
		//Sprite Components
		Sprite playerSprite = new Sprite(texture);
		Sprite swordSprite = new Sprite(Constants.swordTexture);
		
		playerSprite.setScale(PLAYER_SPRITE_SCALE);
		swordSprite.setScale(SWORD_SPRITE_SCALE_X, SWORD_SPRITE_SCALE_Y);
		
		add(new SpriteComponent(playerSprite, swordSprite));
		
		//Components
		add(new VelocityComponent());
		add(new RotationComponent(sword, rotationJoint));
		add(new SpeedComponent(SPEED, STAB_SPEED));
		add(new ControllerComponent(controller));
		add(new BodyComponent(centerBody, bodyFixture, sword, swordFixture));
	}
	
	
}
