import java.util.ArrayList;
public class Team {
	ArrayList<Hero> teamOfHeroes; 
	ArrayList<Monster> teamOfMonsters; 
	
	public Team() {
		
	}
	
	public boolean teamHasWon(ArrayList<LivingCreature> team) {
		for (LivingCreature player : team) {
			if (player.hasWon()) {
				return true; 
			}
		}
		return false; 
	}

}
