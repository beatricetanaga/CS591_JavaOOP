
public enum PotionType {
	HP("Healing Potion", AttributeType.HP), MANA("Magic Potion", AttributeType.MANA), 
	STR("Strength Potion", AttributeType.STR), DEX("Luck Elixer", AttributeType.DEX), 
	AGI("Mermaid Tears", AttributeType.AGI), AMBROSIA("Ambrosia", null);
	
	private String potionName;
	private AttributeType attributeType;
	
	private PotionType(String potionName, AttributeType attributeType) {
		this.potionName = potionName;
		this.attributeType = attributeType;
	}
	
	@Override
	public String toString() {
		return potionName;
	}
	public String getAttributeName() {
		if(this.attributeType != null) {
			return this.attributeType.toString();
		}
		return "N/A";
	}
}
