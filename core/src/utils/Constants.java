package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	
	//Physics Constants
	public static final float TIME_STEP = 1/45f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 2;
	
	
	//Camera Constants
	public static final float V_WIDTH = 800;
	public static final float V_HEIGHT = 800;
	public static final int PPM = 8;
	
	//Textures
	public static final Texture bluePlayerTexture = new Texture(Gdx.files.internal("blue.png"));
	public static final Texture redPlayerTexture = new Texture(Gdx.files.internal("red.png"));
	public static final Texture greenPlayerTexture = new Texture(Gdx.files.internal("green.png"));
	public static final Texture yellowPlayerTexture = new Texture(Gdx.files.internal("yellow.png"));
	
	public static final Texture swordTexture = new Texture(Gdx.files.internal("sword_texture.png"));
	
	//Maps
	public static final TiledMap map = new TmxMapLoader().load("map.tmx");
	
	//Map Scale
	public static final float MAP_SCALE = 1/4f;
	
	
	public static final SpawnInformation[] spawns = {		
			
		//Top Left
		new SpawnInformation(100/Constants.PPM, 550/Constants.PPM, -80 * MathUtils.degreesToRadians, bluePlayerTexture),
		
		//Bottom Right
		new SpawnInformation(550/Constants.PPM, 100/Constants.PPM, 154.5f * MathUtils.degreesToRadians, redPlayerTexture),
			
		//Top Right	
		new SpawnInformation(550/Constants.PPM, 550/Constants.PPM, 200 * MathUtils.degreesToRadians, greenPlayerTexture),
		
		//Bottom Left
		new SpawnInformation(100/Constants.PPM, 100/Constants.PPM, 82.5f * MathUtils.degreesToRadians, yellowPlayerTexture),
		
		
	};
}
