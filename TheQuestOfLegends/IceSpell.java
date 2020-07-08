
public class IceSpell extends Spell {
	//reduces the damage range of the enemy 
	
	public IceSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage) {
		super(id, name, price, minHeroLevel, manaDeduction, damage); 
	}
	
	// method that reduces the monster's damage level by 10%
	public void deteriorateSkill(Monster monster) {
		monster.setDamage(monster.getDamage()*0.9);
	}

}
