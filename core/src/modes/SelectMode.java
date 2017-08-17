package modes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

public class SelectMode implements GameMode{

	@Override
	public boolean endRound(ImmutableArray<Entity> entities) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset(ImmutableArray<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addScore(ImmutableArray<Entity> entities) {
		// TODO Auto-generated method stub
		
	}

}
