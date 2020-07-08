
public abstract class Spell extends Item {
	//magic attack executed by a hero
	//name, price, minimum hero level 
	//damage range
	//amount of magic energy (mana) required to get casted
	//after cast of spell this amount of mana is deducted by hero 
	private int manaDeduction;
	private int damage; 
	
	public Spell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage) {
		super(id, name, price, minHeroLevel); 
		this.manaDeduction = manaDeduction; 
		this.damage = damage; 
	}
	
	public int getManaDeduction() {
		return manaDeduction;
	}

	public void setManaDeduction(int manaDeduction) {
		this.manaDeduction = manaDeduction;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	// abstract method implemented differently in each subclass
	// since different spells deteriorate different skills of the monster
	public abstract void deteriorateSkill(Monster monster);
	
	// method that displays info on the spell
	public void display() {
		System.out.println("ID: " + this.getId() + " Name: " + this.getName() + " Damage: " + getDamage() + " Mana Deduction: " + getManaDeduction()); 
	}
	
	// method that reduces monster's HP by amount of damage caused by spell 
	public void use(Hero hero, Monster monster) {
		if (monster.dodgedAttack()) { // if monster dodges the attack 
			System.out.println(monster.getName().trim() + " dodged the attack!"); 
		} else { // if spell is successfully cast 
			monster.setHp(monster.getHp()- (damage + (hero.getSkill().getDexterity()/10000)*damage));
			deteriorateSkill(monster); 
		}
		hero.setMana(hero.getMana() - manaDeduction); // reduce the hero's mana after casting spell 
	}

}
