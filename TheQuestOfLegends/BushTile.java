import java.util.Scanner;

public class BushTile extends Tile {
	
	public BushTile() {
		borderLabel = "B"; 
	}
	
	public boolean enter(Hero hero, Scanner scan) {
		super.enter(hero, scan); 
		hero.getSkill().setDexterity(hero.getSkill().getDexterity()*1.1); // increase hero's dexterity by 10% 
		return true;
	}

}
