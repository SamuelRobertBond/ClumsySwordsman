package Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import Worlds.PhysicsWorld;

public class DebugRenderSystem extends EntitySystem{

	Box2DDebugRenderer renderer = new Box2DDebugRenderer();
	OrthographicCamera camera;
	
	public DebugRenderSystem(OrthographicCamera camera){
		this.camera = camera;
	}
	
	
	@Override
	public void update(float deltaTime) {
	
		renderer.render(PhysicsWorld.world, camera.combined);
	}
	
}
