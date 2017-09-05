package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import utils.Constants;

public class ControllerComponent implements Component{
	
	private final float DEADZONE = .3f;
	private final float TRIGGER_INPUT_INTESITY = .3f;
	
	public Sound primarySound = Constants.WHOOSH;
	public Sound secondarySound = Constants.WHOOSH;
	
	public Controller controller;
	public boolean connected;
	
	public boolean canUseAbility;
	public boolean released;
	
	private Timer timer;
	
	public ControllerComponent(Controller controller) {
		
		this.controller = controller;
		
		released = true;
		canUseAbility = true;
		timer = new Timer();
		
	}
	

	/**
	 * Returns whether or not the button bound to the primary ability is being pressed
	 * @return
	 */
	public boolean isPrimaryPressed(){
		if(controller.getAxis(4) > TRIGGER_INPUT_INTESITY){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether or not the button bound to the secondary ability is being pressed
	 * @return
	 */
	public boolean isSecondaryPressed(){
		if(controller.getAxis(5) > TRIGGER_INPUT_INTESITY){
			return true;
		}
		
		released = true;
		return false;
	}
	
	/**
	 * Gets the direction that a joystick is being pressed in
	 * @return - Direction Vector
	 */
	public Vector2 getDirection(){
		
		Vector2 dir = new Vector2();
		
		if(Math.abs(controller.getAxis(0)) > DEADZONE){
			dir.x = controller.getAxis(0);
			System.out.println(dir.x);
		}
		if(Math.abs(controller.getAxis(1)) > DEADZONE){
			dir.y = controller.getAxis(1); 
		}
		
		return dir;
	}

	public void scheduleAbilityRefresh(float delay) {
		timer.scheduleTask(new Task(){
			
			@Override
			public void run() {
				canUseAbility = true;
			}
			
		}, delay);
		
	}
	
	
	
	
	

}
