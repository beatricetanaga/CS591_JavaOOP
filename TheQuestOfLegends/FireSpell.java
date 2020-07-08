public class FireSpell extends Spell {
	//reduces the defense level of the enemy  
	
	// constructor 
	public FireSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage) {
		super(id, name, price, minHeroLevel, manaDeduction, damage); 
	}
	
	// method that reduces the monster's defense level by 10% 
	public void deteriorateSkill(Monster monster) {
		monster.setDefense(monster.getDefense()*0.9);
	}

}
