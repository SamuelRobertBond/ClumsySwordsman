package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class ControllerComponent implements Component{
	
	public Controller controller;
	public boolean rotationLocked;
	public boolean rotationLockReleased;
	public boolean canDash;
	
	public boolean dashReleased;
	
	private Timer timer;
	
	public ControllerComponent(Controller controller) {
		this.controller = controller;
		rotationLocked = false;
		rotationLockReleased = true;
		dashReleased = true;
		canDash = true;
		timer = new Timer();
	}

	public void scheduleDash(float delay) {
		timer.scheduleTask(new Task(){
			
			@Override
			public void run() {
				canDash = true;
			}
			
		}, delay);
		
	}
	
	
	
	
	

}
