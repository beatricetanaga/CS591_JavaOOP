import java.util.Scanner;
//A class that represents the tiles of the Monsters' Nexus 
public class MonsterNexusTile extends NexusTile {

	public boolean enter(Hero hero, Scanner scan) {
		heroLabel = hero.getHeroLabel(); 
		System.out.println("V I C T O R Y! You have reached the Monsters' Nexus!"); 
		endGameMessage(); 
		return true;
	}
	
	public void exit(Hero hero) {
		
	}

}
