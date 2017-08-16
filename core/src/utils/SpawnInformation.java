package utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class SpawnInformation {
	
	public float x;
	public float y;
	public float rotation;
	public Texture texture;
	public Color color;
	
	public SpawnInformation(float x, float y, float rotation, Color color, Texture texture) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.texture = texture;
		this.color = color;
	}

}
