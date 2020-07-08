
public class LightningSpell extends Spell {
	//reduces the dodge chance of the enemy 
	
	public LightningSpell (int id, String name, int price, int minHeroLevel, int manaDeduction, int damage) {
		super(id, name, price, minHeroLevel, manaDeduction, damage); 
	}
	
	// method that reduces the dodge probability of a monster by 10%
	public void deteriorateSkill(Monster monster) {
		monster.setDodge(monster.getDodge()*0.9);
	}

}
