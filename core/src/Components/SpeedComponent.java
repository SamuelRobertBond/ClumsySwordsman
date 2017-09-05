package Components;

import com.badlogic.ashley.core.Component;

public class SpeedComponent implements Component{

	public float speed;
	public float stabSpeed;
	
	public SpeedComponent(float speed, float stabSpeed) {
		this.speed = speed * 10;
		this.stabSpeed = stabSpeed;
	}
	
}
