import java.util.*; 
public abstract class Monster extends LivingCreature {
	private double damage; 
	private double defense; 
	private double dodge; 
	
	private String monsterLabel; // NEWLY ADDED

	// constructor 
	public Monster(String name, int level, double hp, double damage, double defense, double dodge) {
		this.name = name; 
		this.level = level; 
		this.hp = hp;
		this.damage = damage; 
		this.defense = defense; 
		this.dodge = dodge; 
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public double getDodge() {
		return dodge;
	}

	public void setDodge(double dodge) {
		this.dodge = dodge;
	}
	
	public String getMonsterLabel() {
		return monsterLabel;
	}
	
	public void setMonsterLabel(String monsterLabel) {
		this.monsterLabel = monsterLabel;
	}
	public int getRowPosition() {
		return rowPosition;
	}
	
	public void setRowPosition(int rowPosition) {
		this.rowPosition = rowPosition;
	}
	
	public int getColPosition() {
		return colPosition;
	}
	
	public void setColPosition(int colPosition) {
		this.colPosition = colPosition;
	}
	
	public QuestMap getMap() {
		return map;
	}
	
	public void setMap(QuestMap map) {
		this.map = map;
	}
	
	// method that checks if the monster dodged the attack based on its dodge probability  
	public boolean dodgedAttack() {
		int randomInteger = (int)(100.0 * Math.random()); 
		if (randomInteger < dodge) {
			return true; 
		}
		return false; 
	}
	
	// method that is called each time a monster attacks a hero in a fight 
	public void attack(Hero hero) {
		if (hero.dodgedAttack()) { // if the hero dodges the attack 
			System.out.println(hero.getName().trim() + " dodged the attack!"); 
		}
		else if (hero.getWornArmor() != null) { // if hero is wearing an armor 
			Armor currArmor = new Armor(); 
			for (Item i : hero.getItems()) {
				if (i.getId() == hero.getWornArmor()) {
					currArmor = (Armor)i; 
					break; 
				}
			}
			hero.setHp(hero.getHp() - damage*0.10 + currArmor.getDefense()*0.025);
			hero.setWornArmor(null);
		}
		else {
			hero.setHp(hero.getHp() - damage*0.10); 
		}
		
	}
	
	public void attack(Monster monster) {
		
	}
	
	// method that checks if the monster has died if their HP is less than 1 (a decimal counts as 0) 
	public boolean hasDied() {
		return (hp < 1); 
	}

	// method that checks if the monster has reached the Heroes' Nexus 
	public boolean hasWon() {
		return (rowPosition == 7); 
	}
	
	// method for monster to make a move 
	public boolean makeMove(Scanner scan) {
		int newRow = rowPosition;
		int newCol = colPosition; 
		
		if (isValidPosition(rowPosition + 1, colPosition)) { // monster moves down
			newRow += 1;  
		} else if (isValidPosition(rowPosition, colPosition + 1)) { // monster moves right
			newCol += 1; 
		} else if (isValidPosition(rowPosition, colPosition - 1)) { // monster moves left 
			newCol -= 1; 
		}
		
		map.getGrid()[rowPosition][colPosition].exit(this);
		setRowPosition(newRow);
		setColPosition(newCol);
		map.getGrid()[newRow][newCol].enter(this);
		return (hasWon()); 
	}
}
