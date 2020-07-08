
public abstract class Monster extends Character{

	private int damage;
	private int defense;
	private int dodgeChance;
	
	public Monster(String name, int level,
			int damage, int defense, int dodge) {
		
		super(name, level);
		this.damage = damage;
		this.defense = defense;
		this.dodgeChance = dodge;
	}
	
	public void printMonsterInformation() {
		
		// return hero's current armor and weapon, and show statistics
		// Name, level, HP, mana, str, dex, agi, exp, NEED TO ADD: additional weapon damage/armor def, weapon and armor
		System.out.printf(
				"Name: %s\n"
				+ "Level: %d\n"
				+ "HP: %d\n",
				this.getName(), this.getLevel(), this.getHp());
		
		System.out.println();
		System.out.printf("%-20s  %5d\n", "Damage:", this.getDamage());
		System.out.printf("%-20s  %5d\n", "Defense:", this.getDefense());
		System.out.printf("%-20s  %5d%%\n", "Dodge chance:", this.getDodgeChance());
		
		System.out.println();
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * @return the dodgeChance
	 */
	public int getDodgeChance() {
		return dodgeChance;
	}

	/**
	 * @param dodgeChance the dodgeChance to set
	 */
	public void setDodgeChance(int dodgeChance) {
		this.dodgeChance = dodgeChance;
	}
	
	public abstract Monster newInstance();
}
