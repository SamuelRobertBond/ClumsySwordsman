package Systems;

import java.util.HashMap;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import Components.BodyComponent;
import Components.ControllerComponent;
import Components.SpeedComponent;
import Components.VelocityComponent;

public class InputSystem extends EntitySystem{

	private final float ABILITY_RECHARGE = .5f;
	
	private ImmutableArray<Entity> entities;
	
	private HashMap<String, Controller> map;
	private Queue<Controller> connectQueue;
	private Queue<Controller> disconnectQueue;
	
	
	private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<ControllerComponent> cm = ComponentMapper.getFor(ControllerComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<SpeedComponent> sm = ComponentMapper.getFor(SpeedComponent.class);
	
	
	public InputSystem() {
		
		map = new HashMap<String, Controller>();
		
		connectQueue = new Queue<Controller>();
		disconnectQueue = new Queue<Controller>();
		
		for(Controller controller : Controllers.getControllers()){
			map.put(controller.toString(), controller);
		}
	}
	
	public void addedToEngine(Engine engine){
		entities = engine.getEntitiesFor(Family.all(BodyComponent.class, ControllerComponent.class, VelocityComponent.class, SpeedComponent.class).get());
		
		Controllers.addListener(new ControllerAdapter(){
			
			@Override
			public void connected(Controller controller) {
				if(disconnectQueue.size > 0){
					connectQueue.addLast(controller);
					System.out.println("Connected :" + controller.toString());
				}
			}
			
			@Override
			public void disconnected(Controller controller) {
				disconnectQueue.addLast(controller);
				System.out.println("Dis :" + controller.toString());
			}
			
		});
		
	}
	
	@Override
	public void update(float deltaTime) {
		
		for (int i = 0; i < entities.size(); ++i) {
			
			Entity entity = entities.get(i);
			
			BodyComponent bc = bm.get(entity);
			ControllerComponent cp = cm.get(entity);
			VelocityComponent vp = vm.get(entity);
			SpeedComponent sp = sm.get(entity);
			
			//Controller (Re)Connection Check
			if(disconnectQueue.size > 0 && connectQueue.size > 0){
				if(cp.controller.equals(disconnectQueue.first())){
					disconnectQueue.removeFirst();
					cp.controller = connectQueue.removeFirst();
				}
			}
			
			
			if(vp.alive){
				
				//Apply Movement
				bc.body.applyLinearImpulse(cp.getDirection().scl(sp.speed), bc.body.getWorldCenter(), true);
				bc.body.resetMassData();
				
				//Abilities
				if(cp.canUseAbility){
					
					Vector2 dir = bc.sword.getTransform().getOrientation();
					
					if(cp.isPrimaryPressed() && cp.released){
						cp.primarySound.play();
						cp.canUseAbility = false;
						cp.released = false;
						cp.scheduleAbilityRefresh(ABILITY_RECHARGE);
						bc.body.applyLinearImpulse(new Vector2(dir.x * -sp.stabSpeed, dir.y * -sp.stabSpeed), bc.body.getWorldCenter(), true);
					}else if(cp.isSecondaryPressed() && cp.released){
						cp.secondarySound.play();
						cp.canUseAbility = false;
						cp.released = false;
						cp.scheduleAbilityRefresh(ABILITY_RECHARGE);
						bc.body.applyLinearImpulse(new Vector2(dir.x * sp.stabSpeed, dir.y * sp.stabSpeed), bc.body.getWorldCenter(), true);
					}else if(!cp.isPrimaryPressed() && !cp.isSecondaryPressed()){
							cp.released = true;
					}
					
				}
			}
			
		}		
		
	}
	
	public void dispose(){
		Controllers.clearListeners();
	}
}
