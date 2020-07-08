import java.util.Scanner;

public class KoulouTile extends Tile {
	
	public KoulouTile() {
		borderLabel = "K"; 
	}
	
	public boolean enter(Hero hero, Scanner scan) {
		super.enter(hero, scan); 
		hero.getSkill().setStrength(hero.getSkill().getStrength()*1.1); // increase hero's strength by 10% 
		return true; 
	}
}
