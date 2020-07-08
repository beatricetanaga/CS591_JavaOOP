
public class Warrior extends Hero{
	
	public static String gameClassName() {
		return "Warrior";
	}

	public Warrior(String name, int mana, int str, int agi, int dex, int money, int exp) {
		super(name, mana, str, agi, dex, money, exp);
	}
	
	@Override
	protected String getGameClass() {
		return Warrior.gameClassName();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	protected boolean canUse(Item item) {
		return true;
	}

	@Override
	protected void calcStats() {
		// TODO Auto-generated method stub
		
	}
}
