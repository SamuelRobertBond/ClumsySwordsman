package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component{

	public Sprite playerSprite;
	public Sprite swordSprite;
	public BitmapFont font;
	
	public float sword_X;
	public float sword_Y;
	public float swordWidth;
	public float swordHeight;
	public float swordRotation;
	
	public SpriteComponent(Sprite player, Sprite sword, BitmapFont font) {
		this.playerSprite = player;
		this.swordSprite = sword;
		
		this.font = font;
	}
	
}
