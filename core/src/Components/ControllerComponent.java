package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.controllers.Controller;

public class ControllerComponent implements Component{
	
	public Controller controller;
	public boolean rotationLocked;
	public boolean rotationLockReleased;
	
	public boolean dashReleased;
	
	public ControllerComponent(Controller controller) {
		this.controller = controller;
		rotationLocked = false;
		rotationLockReleased = true;
		dashReleased = true;
	}
	

}
