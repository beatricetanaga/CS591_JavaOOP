// A class that represents a Nexus Tile, extended by HeroNexusTile and MonsterNexusTile
public class NexusTile extends Tile {

	public NexusTile() {
		borderLabel = "N"; 
	}
	
	public void endGameMessage() {
		System.out.println("The game has ended."); 
	}
}
