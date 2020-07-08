
public class Paladin extends Hero{
	
	public static String gameClassName() {
		return "Paladin";
	}

	public Paladin(String name, int mana, int str, int agi, int dex, int money, int exp) {
		super(name, mana, str, agi, dex, money, exp);
	}
	
	@Override
	protected String getGameClass() {
		return Paladin.gameClassName();
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
