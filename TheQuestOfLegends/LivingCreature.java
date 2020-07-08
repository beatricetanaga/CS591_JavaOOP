import java.util.*; 
// A class that represents the living creatures in the game, a superclass of Monster and Hero
public abstract class LivingCreature implements Fightable {
	public String name; 
	public int level; 
	public double hp;
	public QuestMap map; 
	public int rowPosition; 
	public int colPosition; 
	
	public abstract boolean makeMove(Scanner scan); // abstract method for creature to make a move
	public abstract boolean hasWon(); // abstract method to check if the creature won 
	
	
	// method that checks if the move made by a monster or hero is valid 
	// must be in bounds within the board and cannot be a blocked tile 
	public boolean isValidPosition(int newRow, int newCol) {
		boolean spaceAvailable = false; 
		if (isInBounds(newRow, newCol)) {
			if (this instanceof Hero) {
				spaceAvailable = map.getGrid()[newRow][newCol].getHeroLabel().equals("  "); 
			} else {
				spaceAvailable = map.getGrid()[newRow][newCol].getMonsterLabel().equals("  "); 	
			}
			return (spaceAvailable && (!(map.getGrid()[newRow][newCol] instanceof BlockedTile))); 
		}
		return false; 
	}
	
	public boolean isInBounds(int newRow, int newCol) {
		return (newRow >= 0 && newRow < map.getNumRows() && newCol >= 0 && newCol < map.getNumCols()); 
	}
}
