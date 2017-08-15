package modes;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;

public interface GameMode {
	
	/**
	 * Checks if the round should end
	 * @param entities
	 * @return
	 */
	public boolean endRound(ImmutableArray<Entity> entities);
	
	/**
	 * Checks if the game should end
	 * @return
	 */
	public boolean endGame();
	
	
	/**
	 * Resets the game stage
	 * @param entities
	 */
	public void reset(ImmutableArray<Entity> entities);
	
	/**
	 * Adds the score to the score counter and resets the rounds score
	 * @param roundScores
	 */
	public void addScore(ImmutableArray<Entity> entities);
	
}
