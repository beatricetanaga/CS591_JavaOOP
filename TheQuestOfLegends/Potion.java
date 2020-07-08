
public class Potion extends Item {
	//increase hero's statistics by some amount
	//one-time uses items; cannot be reused
	//price, name, minimum hero level 
	private int incrementStat; 

	public Potion(int id, String name, int price, int minHeroLevel, int incrementStat) {
		super(id, name, price, minHeroLevel); 
		this.incrementStat = incrementStat; 
	}
	
	public int getIncrementStat() {
		return incrementStat;
	}

	public void setIncrementStat(int incrementStat) {
		this.incrementStat = incrementStat;
	}
	
	// method that displays information on the potion in a fight 
	public void display() {
		System.out.println("ID: " + this.getId() + " Name: " + this.getName() + " Strength Increase: " + incrementStat); 
	}
	
	// method that increases hero's strength after drinking potion 
	public void use(Hero hero, Monster monster) {
		hero.getSkill().setStrength(hero.getSkill().getStrength()+ incrementStat);
		hero.getItems().remove(this); // remove the potion from hero's items because it is one-time use 
	}

}
