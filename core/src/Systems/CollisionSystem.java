package Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import Components.BodyComponent;
import Components.RotationComponent;
import Components.ScoreComponent;
import Components.VelocityComponent;
import Entities.Player;
import Worlds.PhysicsWorld;

public class CollisionSystem extends EntitySystem implements ContactListener{

	ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	ComponentMapper<RotationComponent> rm = ComponentMapper.getFor(RotationComponent.class);
	ComponentMapper<ScoreComponent> sm = ComponentMapper.getFor(ScoreComponent.class);
	
	ImmutableArray<Entity> entities;
	
	public CollisionSystem() {
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(BodyComponent.class, VelocityComponent.class, RotationComponent.class).get());
		PhysicsWorld.world.setContactListener(this);
	}
	
	
	@Override
	public void beginContact(Contact contact) {
		
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		
		if(f1.getUserData() != null && f2.getUserData() != null){
			
			if(f1.getUserData().getClass().equals(Player.class) && f2.getUserData().getClass().equals(Player.class)){
				
				Entity a = (Player)f1.getUserData();
				Entity b = (Player)f2.getUserData();
				
				BodyComponent bc1 = bm.get(a);
				BodyComponent bc2 = bm.get(b);
				
				VelocityComponent vp = vm.get(a);
				
				//Check if the collision was between a body and sword
				if(!a.equals(b) && bc1.swordFixture != null && bc2.swordFixture != null ){
					if(bc1.swordFixture.equals(f1) && bc2.bodyFixture.equals(f2)){
						//Disables Movement
						vp = vm.get(b);
						
						if(vp.alive != false){
							vp.alive = false;
							RotationComponent rc = rm.get(b);
							
							if(rc.weldJoint != null){
								PhysicsWorld.queueJointDestory(rc.weldJoint);
								PhysicsWorld.queueFixtureDestory(bc2.sword, bc2.swordFixture);
								bc2.swordFixture = null;
								rc.weldJoint = null;
							}
						}
					}
				}
			}else{
				
				//Checks if the fixtures were the score box
				if(f1.getUserData().getClass().equals(ScoreComponent.class)){
					ScoreComponent sc = (ScoreComponent)f1.getUserData();
					++sc.bodiesOccupied;
				}else if(f2.getUserData().getClass().equals(ScoreComponent.class)){
					ScoreComponent sc = (ScoreComponent)f2.getUserData();
					++sc.bodiesOccupied;
				}
				
			}
		}

	}

	
	//Ending of Stabing
	@Override
	public void endContact(Contact contact) {
		
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		
		if(f1.getUserData() != null && f2.getUserData() != null){
			
			if(f1.getUserData().getClass().equals(ScoreComponent.class)){
				ScoreComponent sc = (ScoreComponent)f1.getUserData();
				--sc.bodiesOccupied;
			}else if(f2.getUserData().getClass().equals(ScoreComponent.class)){
				ScoreComponent sc = (ScoreComponent)f2.getUserData();
				--sc.bodiesOccupied;
			}
			
		}
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
