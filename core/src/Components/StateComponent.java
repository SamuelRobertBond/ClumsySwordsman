package Components;

import utils.PlayerState;

public class StateComponent {

	public PlayerState alive;
	
	public StateComponent() {
		alive = PlayerState.ALIVE;
	}
	
}
