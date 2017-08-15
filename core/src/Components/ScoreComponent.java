package Components;

import com.badlogic.ashley.core.Component;

public class ScoreComponent implements Component{

	public boolean alive = true;
	
	public int kills = 0;
	public int deaths = 0;
	public int seconds = 0;
	
	public int score = 0;
	
	public ScoreComponent() {
	}
	
	public void reset(){
		kills = 0;
		deaths = 0;
		seconds = 0;
	}
	
}
