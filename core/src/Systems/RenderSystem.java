package Systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import Components.BodyComponent;
import Components.SpriteComponent;
import utils.Constants;

public class RenderSystem extends EntitySystem{

	private ImmutableArray<Entity> entities;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	
	public RenderSystem(OrthographicCamera camera) {
		
		this.camera = camera;
		batch = new SpriteBatch();
		
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, BodyComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		batch.setProjectionMatrix(camera.combined);
		
		for(int i = 0; i < entities.size(); ++i){
			
			Entity entity = entities.get(i);
			
			BodyComponent bc = bm.get(entity);
			SpriteComponent sc = sm.get(entity);
			
			batch.begin();
			
			//Player Sprite
			sc.playerSprite.setPosition(bc.body.getPosition().x - sc.playerSprite.getWidth()/2, bc.body.getPosition().y - sc.playerSprite.getHeight()/2);
			sc.playerSprite.setRotation(bc.sword.getAngle() * MathUtils.radiansToDegrees);
			
			//Sword Sprite
			sc.swordSprite.setPosition(bc.sword.getPosition().x - sc.swordSprite.getWidth()/2, bc.sword.getPosition().y - sc.swordSprite.getHeight()/2);
			sc.swordSprite.setRotation(bc.sword.getAngle() * MathUtils.radiansToDegrees);
			
			//Draw
			sc.swordSprite.draw(batch);
			sc.playerSprite.draw(batch);
			
			batch.end();
			
		}
		
	}
	
}
