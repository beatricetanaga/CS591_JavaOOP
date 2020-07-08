import java.util.*; 
// Class that represents a single cell of the map's grid 
public abstract class Tile {
	protected String label; 
	protected String heroLabel;
	protected String monsterLabel; 
	protected String borderLabel = "?"; 
	
	public Tile() {
		label = " "; 
		heroLabel = "  "; 
		monsterLabel = "  "; 
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	} 
	
	public String getBorderLabel() {
		return borderLabel;
	}

	public void setBorderLabel(String borderLabel) {
		this.borderLabel = borderLabel;
	}
	
	public String getHeroLabel() {
		return heroLabel;
	}

	public void setHeroLabel(String heroLabel) {
		this.heroLabel = heroLabel;
	}

	public String getMonsterLabel() {
		return monsterLabel;
	}

	public void setMonsterLabel(String monsterLabel) {
		this.monsterLabel = monsterLabel;
	}
	
	public void printTile() {
		System.out.println(borderLabel + " - " + borderLabel + " - " + borderLabel); 
		System.out.println("| " + label + " " + label + " |"); 
		System.out.println(borderLabel + " - " + borderLabel + " - " + borderLabel); 
		
	}
	
	public boolean enter(Monster monster) {
		monsterLabel = monster.getMonsterLabel(); 
		return true; 
	}
	
	public void exit(Monster monster) {
		monsterLabel = "  "; 
	}
	
	public void exit(Hero hero) {
		heroLabel = "  "; 
	}
	
	public boolean enter(Hero hero, Scanner scan) {
		heroLabel = hero.getHeroLabel(); 
		return true;
	}
	

}
