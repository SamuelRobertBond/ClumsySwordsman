package Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component{

	public Sprite playerSprite;
	public Sprite swordSprite;
	
	public SpriteComponent(Sprite player, Sprite sword) {
		this.playerSprite = player;
		this.swordSprite = sword;
	}
	
}
