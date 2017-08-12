package Components;

import com.badlogic.ashley.core.Component;

public class SpeedComponent implements Component{

	public float speed = 0f;
	public float stabSpeed;
	
	public SpeedComponent(float speed, float stabSpeed) {
		this.speed = speed;
		this.stabSpeed = stabSpeed;
	}
	
}
