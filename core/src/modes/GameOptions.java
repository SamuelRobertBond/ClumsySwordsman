package modes;

public class GameOptions {

	public int rounds = 5;
	public int kills = 5;
	public int seconds = 30;
	
	public GameOptions() {
	
	}
	
	public GameOptions(int rounds, int kills, int seconds){
		
		this.rounds = rounds;
		this.kills = kills;
		this.seconds = seconds;
		
	}
	
}
