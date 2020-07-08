
public class Sorcerer extends Hero{
	
	public static String gameClassName() {
		return "Sorcerer";
	}

	public Sorcerer(String name, int mana, int str, int agi, int dex, int money, int exp) {
		super(name, mana, str, agi, dex, money, exp);
	}
	
	@Override
	protected String getGameClass() {
		return Sorcerer.gameClassName();
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
