package Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import utils.Constants;

public class MapRenderingSystem extends EntitySystem{
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	public MapRenderingSystem(OrthographicCamera camera, TiledMap map) {
		this.renderer = new OrthogonalTiledMapRenderer(map, Constants.MAP_SCALE);
		this.camera = camera;
		
	}
	
	@Override
	public void update(float deltaTime) {
		renderer.setView(camera);
		renderer.render();
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			camera.translate(1, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			camera.translate(-1, 0);
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)){
			camera.translate(0, 1);
		}
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			camera.translate(0, -1);
			System.out.println(camera.position);
		}
		
		if(Gdx.input.isButtonPressed(Buttons.BACK)){
			camera.zoom -= .02f;
			System.out.println(camera.zoom);
		}
		
		if(Gdx.input.isButtonPressed(Buttons.FORWARD)){
			camera.zoom += .02f;
			System.out.println(camera.zoom);
		}
		
		camera.update();
		
	}

}
