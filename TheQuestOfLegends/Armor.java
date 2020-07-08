
public class Armor extends Item {
	//reduces the incoming damage from the enemy's attacks
	//price, name, minimum hero level 
	private int defense; 

	public Armor(int id, String name, int price, int minHeroLevel, int defense) {
		super(id, name, price, minHeroLevel); 
		this.defense = defense; 
	}
	
	public Armor() {
		
	}
	
	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	// method to display info about the armor in a fight 
	public void display() {
		System.out.println("ID: " + this.getId() + " Name: " + this.getName() + " Defense: " + defense); 
	}
	
	// wearing an armor reduces the damage received from the monster 
	// set the hero's armor to the armor being used 
	public void use(Hero hero, Monster monster) {
		hero.setWornArmor(getId()); 
	}

}
