package utils;


import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MapUtils {

	public static TiledMap createMap(TiledMap map){
		
		MapLayer walls =  map.getLayers().get("Walls");
		
		
		for(MapObject wall : walls.getObjects()){
			
			//Gets the values
			float x = (Float) wall.getProperties().get("x");
			float y = (Float) wall.getProperties().get("y");
			float width = (Float) wall.getProperties().get("width");
			float height = (Float) wall.getProperties().get("height");
			
			System.out.println("Default");
			System.out.println("-----------------------");
			System.out.println("X: " + x + ", Y: " + y + "\nWidth: " + width + "\nHeight: " + height);
			System.out.println("-----------------------");
			
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
	
}
