package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class ScoreComponent implements Component{

	public boolean alive = true;
	
	public int kills = 0;
	public int deaths = 0;
	public int seconds = 0;
	
	public int score = 0;
	
	public float xpos;
	public float ypos;
	
	public Body body;
	public Fixture fontFixture;
	public float alpha;
	
	public int bodiesOccupied;
	
	public ScoreComponent(float x, float y, Body fontBody, Fixture fontFixture) {
		xpos = x;
		ypos = y;
		body = fontBody;
		this.fontFixture = fontFixture;
		this.alpha = 1;
		this.bodiesOccupied = 0;
	}
	
	public void reset(){
		kills = 0;
		deaths = 0;
		seconds = 0;
	}
	
}
