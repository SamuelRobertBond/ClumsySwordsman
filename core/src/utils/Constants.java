package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.MathUtils;

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
	
	//Map Scale
	public static final float MAP_SCALE = 1/4f;
	
	//Font font
	public static final FileHandle FONT_FILE = Gdx.files.internal("Fonts/Tahoma.ttf");
	
	
	//Sounds Handles
	public static final FileHandle CLAPPING = Gdx.files.internal("Sounds/clapping.wav");
	public static final FileHandle BEEP = Gdx.files.internal("Sounds/beep.wav");
	public static final FileHandle BEEP_JOIN = Gdx.files.internal("Sounds/beep2.wav");
	public static final Sound WHOOSH = Gdx.audio.newSound(Gdx.files.internal("Sounds/whoosh.wav")); // For some reason OpenAL does not like this sound being created during runtime (probably a compression issue)
	
	
}
