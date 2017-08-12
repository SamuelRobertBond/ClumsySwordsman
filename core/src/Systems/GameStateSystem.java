package Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;

import Components.RotationComponent;
import Components.SpawnComponent;

public class GameStateSystem extends EntitySystem{
	
	
	private ComponentMapper<SpawnComponent> sm = ComponentMapper.getFor(SpawnComponent.class);
	private ImmutableArray<Entity> entities;
	
	public GameStateSystem() {
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		// TODO Auto-generated method stub
		super.addedToEngine(engine);
	}
	
	

}
