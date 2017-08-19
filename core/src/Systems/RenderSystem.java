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
import Components.ScoreComponent;
import Components.SpriteComponent;

public class RenderSystem extends EntitySystem{

	private ImmutableArray<Entity> entities;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private final float FADE_SPEED = 2f;
	
	private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
	private ComponentMapper<ScoreComponent> scm = ComponentMapper.getFor(ScoreComponent.class);
	
	public RenderSystem(OrthographicCamera camera) {
		
		this.camera = camera;
		batch = new SpriteBatch();
		
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(SpriteComponent.class, BodyComponent.class, ScoreComponent.class).get());
	}
	
	@Override
	public void update(float deltaTime) {
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//Render Scores
		for(int i = 0; i < entities.size(); ++i){
			
			Entity entity = entities.get(i);
			SpriteComponent sc = sm.get(entity);
			ScoreComponent scc = scm.get(entity);
			
			if(scc.renderScore){
				if(scc.bodiesOccupied == 0){
					scc.alpha += FADE_SPEED * deltaTime;
					if(scc.alpha >1){
						scc.alpha = 1f;
					}
					
				}else{
					scc.alpha -= FADE_SPEED * deltaTime;
					if(scc.alpha < .2){
						scc.alpha = .2f;
					}
				}
				
				sc.font.setColor(sc.font.getColor().r, sc.font.getColor().g, sc.font.getColor().b, scc.alpha);
				sc.font.draw(batch, scc.score + "", scc.xpos, scc.ypos);
			}
			
		}
		
		//Render Swords
		for(int i = 0; i < entities.size(); ++i){
			
			Entity entity = entities.get(i);
			
			BodyComponent bc = bm.get(entity);
			SpriteComponent sc = sm.get(entity);
			
			
			if(bc.swordFixture != null){
				sc.sword_X = bc.sword.getPosition().x - sc.swordSprite.getWidth()/2;
				sc.sword_Y = bc.sword.getPosition().y - sc.swordSprite.getHeight()/2;
				sc.swordSprite.setRotation(bc.sword.getAngle() * MathUtils.radiansToDegrees);
			}
			
			//Sword Sprite
			sc.swordSprite.setPosition(sc.sword_X, sc.sword_Y);
			
			//Draw
			sc.swordSprite.draw(batch);
			
		}
		
		//Render Players
		for(int i = 0; i < entities.size(); ++i){
			
			Entity entity = entities.get(i);
			
			BodyComponent bc = bm.get(entity);
			SpriteComponent sc = sm.get(entity);
			
			//Player Sprite
			sc.playerSprite.setPosition(bc.body.getPosition().x - sc.playerSprite.getWidth()/2, bc.body.getPosition().y - sc.playerSprite.getHeight()/2);
			sc.playerSprite.setRotation(bc.sword.getAngle() * MathUtils.radiansToDegrees);
			
			//Draw
			sc.playerSprite.draw(batch);
			
			
		}
		
		batch.end();
		
	}
	
	public void dispose(){
		batch.dispose();
	}
	
}
