package Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.math.Vector2;

import Components.BodyComponent;
import Components.ControllerComponent;
import Components.RotationComponent;
import Components.SpeedComponent;
import Components.VelocityComponent;
import net.dermetfan.gdx.math.MathUtils;

public class InputSystem extends EntitySystem{

	private final float DEADZONE = .3f;
	
	private ImmutableArray<Entity> entities;
	
	private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<ControllerComponent> cm = ComponentMapper.getFor(ControllerComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);
	private ComponentMapper<RotationComponent> rm = ComponentMapper.getFor(RotationComponent.class);
	
	
	public InputSystem() {
		
	}
	
	public void addedToEngine(Engine engine){
		entities = engine.getEntitiesFor(Family.all(ControllerComponent.class, VelocityComponent.class, SpeedComponent.class, RotationComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		
		//Replace this with Controller input component for the ability to add more controller types
		int leftAxisCodeX = 1;
		int leftAxisCodeY = 0;
		
		for (int i = 0; i < entities.size(); ++i) {
			
			Entity entity = entities.get(i);
			
			BodyComponent bc = bm.get(entity);
			ControllerComponent cp = cm.get(entity);
			VelocityComponent vp = vm.get(entity);
			SpeedComponent sp = sm.get(entity);
			RotationComponent rp = rm.get(entity);
			
			if(vp.alive){
				
				float leftInputIntensity = Math.abs(cp.controller.getAxis(leftAxisCodeX)) + Math.abs(cp.controller.getAxis(leftAxisCodeY));
				
				//Left Analog Input
				if(leftInputIntensity > DEADZONE){
					vp.x = cp.controller.getAxis(leftAxisCodeX) * sp.speed * 10;
					vp.y = -cp.controller.getAxis(leftAxisCodeY) * sp.speed * 10;
				}else{
					vp.x = 0;
					vp.y = 0;
				}
				
				
				//Apply Linear Force 
				bc.body.applyLinearImpulse(new Vector2(vp.x,  vp.y), bc.body.getWorldCenter(), true);
				bc.body.resetMassData();
				
				
				// Enable / Disable Rotation
				if(cp.controller.getButton(4) && cp.rotationLockReleased){
					
					if(!cp.rotationLocked){
						
						rp.revoluteJoint.setLimits(rp.revoluteJoint.getJointAngle(), rp.revoluteJoint.getJointAngle());
						rp.revoluteJoint.enableLimit(true);
						cp.rotationLocked = true;
						
						Gdx.app.log("Button Pressed", "Locked");
						
					}else{
						rp.revoluteJoint.enableLimit(false);
						cp.rotationLocked = false;
						Gdx.app.log("Button Pressed", "Unlocked");
					}
					
					cp.rotationLockReleased = false;
					
				}else if(!cp.controller.getButton(4) && !cp.rotationLockReleased){
					cp.rotationLockReleased = true;
				}
				
				
				//Dash Attacks
				float dashInput = cp.controller.getAxis(4);
				if(Math.abs(dashInput) > .4f && cp.dashReleased){
					Vector2 dir = bc.sword.getTransform().getOrientation();
					cp.dashReleased = false;
					if(dashInput > .4f){
						bc.body.applyLinearImpulse(new Vector2(dir.x * -sp.stabSpeed, dir.y * -sp.stabSpeed), bc.body.getWorldCenter(), true);
						Gdx.app.log("ButtonPressed", "Reverse Dash");
					}else{
						bc.body.applyLinearImpulse(new Vector2(dir.x * sp.stabSpeed, dir.y * sp.stabSpeed), bc.body.getWorldCenter(), true);
						Gdx.app.log("ButtonPressed", "Forward Dash");
					}
				}else{
					if(Math.abs(dashInput) < .2f && !cp.dashReleased){
						cp.dashReleased = true;
					}
				}
			}
			
		}		
		
	}
	
}
