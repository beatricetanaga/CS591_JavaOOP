import java.util.Scanner;

public class CaveTile extends Tile {
	
	public CaveTile() {
		borderLabel = "C"; 
	}
	
	public boolean enter(Hero hero, Scanner scan) {
		super.enter(hero, scan); 
		hero.getSkill().setAgility(hero.getSkill().getAgility()*1.1); // increase hero's agility by 10% 
		return true;
	}

}
