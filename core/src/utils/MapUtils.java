package utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MapUtils {

	//Total amount of maps that the game can load
	public static final int MAP_COUNT = 5;
	
	/**
	 * Loads the specified tiled map
	 * @param mapIndex - the index of the map to load (0 for selection map)
	 * @return
	 */
	public static TiledMap loadMap(int mapIndex){
		
		Gdx.app.log("MapUtils - loadMap", "Loading Map: " + mapIndex);
		
		TiledMap MAP = null;
		TmxMapLoader loader = new TmxMapLoader();
		Parameters params = new Parameters();
		
		params.textureMinFilter = TextureFilter.Nearest;
		params.textureMagFilter = TextureFilter.Nearest;
		
		if(mapIndex == 0){
			MAP = loader.load("Maps/selection.tmx", params);
		}else{
			MAP = loader.load("Maps/map_" + mapIndex + ".tmx", params);
		}
		
		return MAP;

	}
	
	public static TiledMap createMap(TiledMap map){
		
		MapLayer walls =  map.getLayers().get("Walls");
		
		
		for(MapObject wall : walls.getObjects()){
			
			//Gets the values
			float x = (Float) wall.getProperties().get("x");
			float y = (Float) wall.getProperties().get("y");
			float width = (Float) wall.getProperties().get("width");
			float height = (Float) wall.getProperties().get("height");
			
			
			//Calculates the values on an 800 x 800 grid
			width = width/2 * Constants.MAP_SCALE;
			height = height/2 * Constants.MAP_SCALE;
			x = x * Constants.MAP_SCALE + width;
			y = y * Constants.MAP_SCALE + height;

			
			Body body = CollisionUtils.defineBody(x , y, 0, BodyType.StaticBody);
			CollisionUtils.defineRectangularFixture(width, height, 1, 0, false, body);

		}
		
		
		return map;
	}
	
	/**
	 * Retrieves the spawn information from a tiled map
	 * @param map - the map to retrieve span information
	 * @return all the possible spawns on a map
	 */
	public static SpawnInformation[] getSpawnInformation(TiledMap map){
		
		MapLayer spawns =  map.getLayers().get("Spawns");
		
		SpawnInformation info[] = new SpawnInformation[spawns.getObjects().getCount()];
		
		for(MapObject spawn : spawns.getObjects()){
			
			//Gets the values
			float x = (Float) spawn.getProperties().get("x");
			float y = (Float) spawn.getProperties().get("y");
			float width = (Float) spawn.getProperties().get("width");
			float height = (Float) spawn.getProperties().get("height");
			int i = (Integer) spawn.getProperties().get("index");
			
			//Calculates the values on an 800 x 800 grid
			width = width/2 * Constants.MAP_SCALE;
			height = height/2 * Constants.MAP_SCALE;
			x = x * Constants.MAP_SCALE + width/2;
			y = y * Constants.MAP_SCALE + height/2;
			
			
			if(i == 0){
				//Top Left
				info[0] = new SpawnInformation(x, y, -80 * MathUtils.degreesToRadians, Color.BLUE, Constants.BLUE_PLAYER_TEXTURE);
			}else if(i == 1){
				//Bottom Right
				info[1] = new SpawnInformation(x, y, 154.5f * MathUtils.degreesToRadians, Color.RED, Constants.RED_PLAYER_TEXTURE);
			}else if(i == 2){
				//Top Right
				info[2] = new SpawnInformation(x, y, 200 * MathUtils.degreesToRadians, Color.GREEN, Constants.GREEN_PLAYER_TEXTURE);
			}else if(i == 3){
				//Bottom Left
				info[3] = new SpawnInformation(x, y, 82.5f * MathUtils.degreesToRadians, Color.YELLOW, Constants.YELLOW_PLAYER_TEXTURE);
			}

			Gdx.app.log("MapUtils - getSpawnInformation", "Info " + i + ": " + info[i]);
			
		}
		
		return info;
		
	}
	
}
