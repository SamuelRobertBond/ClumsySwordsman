package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public class BodyComponent implements Component{

	public Body body;
	public Body sword;
	public Fixture bodyFixture;
	public Fixture swordFixture;
	
	public BodyComponent(Body body, Fixture bodyFixture, Body sword, Fixture swordFixture){
		this.body = body;
		this.sword = sword;
		
		this.bodyFixture = bodyFixture;
		this.swordFixture = swordFixture;

	}
	
	
}
