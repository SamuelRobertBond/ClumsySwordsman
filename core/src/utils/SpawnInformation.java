package utils;

import com.badlogic.gdx.graphics.Texture;

public class SpawnInformation {
	
	public float x;
	public float y;
	public float rotation;
	public Texture texture;
	
	public SpawnInformation(float x, float y, float rotation, Texture texture) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
		this.texture = texture;
	}

}
