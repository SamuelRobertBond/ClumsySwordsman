package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SpawnComponent implements Component{

	public Vector2 position;
	public float rotation;
	
	public SpawnComponent(Vector2 position, float rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
}
