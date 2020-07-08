
public enum AttributeType {
	HP("Health Points"), MANA("Mana"), STR("Strength"), DEX("Dexterity"), AGI("Agility");
	
	private String enumText;
	
	private AttributeType(String text) {
		this.enumText = text;
	}
	
	@Override
	public String toString() {
		return enumText;
	}
}
