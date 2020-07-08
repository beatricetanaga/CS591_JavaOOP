// Represents a tile that is inaccessible to heroes and monsters 
public class BlockedTile extends Tile {
	
	public BlockedTile() {
		borderLabel = "I"; 
		heroLabel = "X X"; 
		monsterLabel = "X"; 
	}
	
}
