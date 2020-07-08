public class Sorcerer extends Hero {
	//favored on dexterity and agility
	public Sorcerer (int id, String name, int level, double hp, double mana, Skill skill, double money, int experience) {
		super(id, name, level, hp, mana, skill, money, experience); 
	}
	
	// method that upgrades a sorcerer's skills when they level up 
	public void upgradeSkills() {
		this.getSkill().setStrength(this.getSkill().getStrength()*1.05); 
		this.getSkill().setAgility(this.getSkill().getAgility()*1.10);
		this.getSkill().setDexterity(this.getSkill().getDexterity()*1.10);
		
	}

}
