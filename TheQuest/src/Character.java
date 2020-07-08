
/**
 * This class represents a Character in the game.
 *
 */
public abstract class Character {
	
	private String name;
	private int level;
	private int hp;
	
	/**
	 * Creates a character.
	 * 
	 * @param name the character's name
	 * @param level the character's level
	 * @param hp the character's hp
	 */
	public Character(String name, int level) {
		this.name = name;
		this.level = level;
		this.hp = getMaxHP();
	}
	
	/**
	 * Creates a character.
	 * 
	 * @param name the character's name
	 * @param level the character's level
	 * @param hp the character's hp
	 */
	public Character(String name, int level, int hp) {
		this.name = name;
		this.level = level;
		this.hp = hp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = Math.min(hp, getMaxHP());
	}
	
	protected int getMaxHP() {
		return this.getLevel() * 100;
	}
}
