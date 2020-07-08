
public class Weapon extends Item {
	//used to attack a monster
	//name, price, minimum hero level
	//amount of damage it can inflict
	//only one weapon can be held by a hero at a time 
	
	private int damage; 
	private int numHands; 

	
	// constructor 
	public Weapon(int id, String name, int price, int minHeroLevel, int damage, int numHands) {
		super(id, name, price, minHeroLevel); 
		this.damage = damage; 
		this.numHands = numHands; 
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	// method that displays info on a weapon in a fight 
	public void display() {
		System.out.println("ID: " + this.getId() + " Name: " + this.getName() + " Damage: " + damage); 
	}
	
	// method that reduces monster's HP by amount of damage caused by weapon 
	public void use(Hero hero, Monster monster) {
		if (monster.dodgedAttack()) { // if monster dodges an attack 
			System.out.println(monster.getName().trim() + " dodged the attack!"); 
		}
		else { // if monster doesn't dodge attack
			monster.setHp(monster.getHp() - (hero.getSkill().getStrength() + damage - monster.getDefense())*0.05);
		}
	}
}
