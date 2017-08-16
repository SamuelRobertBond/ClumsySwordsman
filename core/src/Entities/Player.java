package Entities;

import java.util.Stack;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;

import Components.BodyComponent;
import Components.ControllerComponent;
import Components.RotationComponent;
import Components.ScoreComponent;
import Components.SpeedComponent;
import Components.SpriteComponent;
import Components.VelocityComponent;
import Worlds.PhysicsWorld;
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
	
	private final int FONT_SIZE = 256;
	
	private ControllerComponent controllerComponent;
	private ScoreComponent scoreComponent;
	
	//Spawn Information
	private float x;
	private float y;
	private float rotation;
	private Texture texture;
	private Controller controller;
	
	private Stack<Body> bodies;
	private BitmapFont font;
	
	/*
	 * This is the actor that the player directly controls
	 * float x - x position
	 * float y - y positionS
	 * Controller controller - Gamepad that controls the player;
	 */
	public Player(float x, float y, float rotation, Texture texture, Color color, Controller controller) {
		
		//Setting Spawn Information
		this.x = x;
		this.y= y;
		this.rotation = rotation;
		this.texture = texture;
		this.controller = controller;
		
		//Font Generation
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Constants.FONT_FILE);
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		
		parameter.size = (int) Math.ceil(Gdx.graphics.getWidth() / Constants.V_WIDTH) * FONT_SIZE;
		
		parameter.color = color;
		parameter.genMipMaps = true;
		
		//Font
		font = generator.generateFont(parameter);
		font.getData().setScale(0.03f);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//Body Storage
		bodies = new Stack<Body>();
		
		//Font Body - Used for font dimming

		scoreComponent = new ScoreComponent(x, y, null, null);
		
		reset();

	}
	
	public void reset(){
		
		removeAll();
		
		while(!bodies.isEmpty()){
			Gdx.app.log("Reseting", "Destroying Bodies");
			PhysicsWorld.queueBodyDestory(bodies.pop());
		}
		
		//
		Body fontBody = CollisionUtils.defineBody(x, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture fontFixture = CollisionUtils.defineRectangularFixture(RADIUS * ((scoreComponent.score + "").length()), RADIUS, 1, 0, true, fontBody);
		fontFixture.setUserData(scoreComponent);
		scoreComponent.body = fontBody;
		scoreComponent.fontFixture = fontFixture;
		
		//Defines the players center body
		Body centerBody = CollisionUtils.defineBody(x, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture bodyFixture = CollisionUtils.defineCircularFixture(DENSITY, RADIUS, false, centerBody);
		bodyFixture.setUserData(this);
		centerBody.setFixedRotation(true);
		centerBody.resetMassData();

		//Defines connection body for the sword and center
		//This is need to apply two different joints
		Body rotationBody = CollisionUtils.defineBody(x, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture rotFixture = CollisionUtils.defineRectangularFixture(ROTATION_JOINT_WIDTH, ROTATION_JOINT_HEIGHT, 1, 0, false, rotationBody);
		rotationBody.setTransform(rotationBody.getPosition(), rotation);
		rotationBody.setAngularDamping(100000);

		//Connects the circular bodies using the revolute joint
		RevoluteJoint rotationJoint = CollisionUtils.createRevoluteJoint(centerBody, rotationBody, ROTATION_SPEED);

		//Creates the sword body
		Body sword = CollisionUtils.defineBody(x + RADIUS, y, LINEAR_DAMPENING, BodyType.DynamicBody);
		Fixture swordFixture = CollisionUtils.defineRectangularFixture(SWORD_WIDTH, SWORD_HEIGHT, SWORD_DENSITY, 0, false, sword);
		swordFixture.setUserData(this);

		//Connects the sword body to the rotationBody with a weld joint
		WeldJoint weldJoint = CollisionUtils.createWeldJoint(SWORD_DISTANCE, rotationBody, sword);
		
		//Sprite Components
		Sprite playerSprite = new Sprite(texture);
		Sprite swordSprite = new Sprite(Constants.SWORD_TEXTURE);
		

		bodies.push(centerBody);
		bodies.push(rotationBody);
		bodies.push(sword);
		bodies.push(fontBody);
		
		playerSprite.setScale(PLAYER_SPRITE_SCALE);
		swordSprite.setScale(SWORD_SPRITE_SCALE_X, SWORD_SPRITE_SCALE_Y);
		
		scoreComponent.xpos = x - RADIUS;
		scoreComponent.ypos = y + RADIUS;

		add(new SpriteComponent(playerSprite, swordSprite, font));
		add(new VelocityComponent());
		add(new RotationComponent(sword, rotationJoint, weldJoint));
		add(new SpeedComponent(SPEED, STAB_SPEED));
		add(new BodyComponent(centerBody, bodyFixture, sword, swordFixture, rotFixture));
		add(new ControllerComponent(controller));
		add(scoreComponent);
		
	}
	
}
