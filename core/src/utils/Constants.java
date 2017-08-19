package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
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
	public static final Texture BLUE_PLAYER_TEXTURE = new Texture(Gdx.files.internal("Textures/blue.png"));
	public static final Texture RED_PLAYER_TEXTURE = new Texture(Gdx.files.internal("Textures/red.png"));
	public static final Texture GREEN_PLAYER_TEXTURE = new Texture(Gdx.files.internal("Textures/green.png"));
	public static final Texture YELLOW_PLAYER_TEXTURE = new Texture(Gdx.files.internal("Textures/yellow.png"));
	
	public static final Texture SWORD_TEXTURE = new Texture(Gdx.files.internal("Textures/sword_texture.png"));
	
	//Maps
	public static TiledMap MAP;
	public static TiledMap SELECTION_MAP;
	
	public static void loadMaps(){
		TmxMapLoader loader = new TmxMapLoader();
		Parameters params = new Parameters();
		
		params.textureMinFilter = TextureFilter.Nearest;
		params.textureMagFilter = TextureFilter.Nearest;
		
		SELECTION_MAP = loader.load("Maps/selection.tmx", params);
		MAP = loader.load("Maps/map.tmx", params);
	}
	
	//Map Scale
	public static final float MAP_SCALE = 1/4f;
	
	//Spawn information for the first map
	public static final SpawnInformation[] spawns = {		
			
		//Top Left
		new SpawnInformation(100/Constants.PPM, 550/Constants.PPM, -80 * MathUtils.degreesToRadians, Color.BLUE, BLUE_PLAYER_TEXTURE),
		
		//Bottom Right
		new SpawnInformation(550/Constants.PPM, 100/Constants.PPM, 154.5f * MathUtils.degreesToRadians, Color.RED, RED_PLAYER_TEXTURE),
			
		//Top Right	
		new SpawnInformation(550/Constants.PPM, 550/Constants.PPM, 200 * MathUtils.degreesToRadians, Color.GREEN, GREEN_PLAYER_TEXTURE),
		
		//Bottom Left
		new SpawnInformation(100/Constants.PPM, 100/Constants.PPM, 82.5f * MathUtils.degreesToRadians, Color.YELLOW, YELLOW_PLAYER_TEXTURE),
		
		
	};
	
	//Font font
	public static final FileHandle FONT_FILE = Gdx.files.internal("Fonts/Tahoma.ttf");
	
	
	//Sounds Handles
	public static final FileHandle CLAPPING = Gdx.files.internal("Sounds/clapping.wav");
	public static final FileHandle BEEP = Gdx.files.internal("Sounds/beep.wav");
	public static final FileHandle BEEP_JOIN = Gdx.files.internal("Sounds/beep2.wav");
	public static final FileHandle WHOOSH = Gdx.files.internal("Sounds/Whoosh.wav");
	
}
