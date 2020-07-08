
public enum MonsterType {
	DRAGON("Dragon"), EXOSKELETON("Exoskeleton"), SPIRIT("Spirit");
	
	private String enumText;
	
	private MonsterType(String text) {
		this.enumText = text;
	}
	
	@Override
	public String toString() {
		return enumText;
	}
}
