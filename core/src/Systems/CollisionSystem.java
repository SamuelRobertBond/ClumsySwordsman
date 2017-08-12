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
import Entities.Player;
import Worlds.PhysicsWorld;

public class CollisionSystem extends EntitySystem implements ContactListener{

	ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	
	ImmutableArray<Entity> entities;
	
	public CollisionSystem() {
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(BodyComponent.class).get());
		PhysicsWorld.world.setContactListener(this);
	}
	
	
	@Override
	public void beginContact(Contact contact) {
		
		Fixture f1 = contact.getFixtureA();
		Fixture f2 = contact.getFixtureB();
		
		if(f1.getUserData() != null && f2.getUserData() != null){
			Entity a = (Player)f1.getUserData();
			Entity b = (Player)f2.getUserData();
			
			BodyComponent bc1 = bm.get(a);
			BodyComponent bc2 = bm.get(b);
			
			//Check if the collision was between a body and sword
			if(!bc1.equals(bc2)){
				if(bc1.swordFixture.equals(f1) && bc2.bodyFixture.equals(f2)){
					System.out.println("Stab 1");
				}else if(bc1.swordFixture.equals(f2) && bc2.bodyFixture.equals(f1)){
					System.out.println("Stab 2");
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
