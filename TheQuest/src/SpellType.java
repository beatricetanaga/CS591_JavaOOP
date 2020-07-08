
public enum SpellType {
	ICE("Ice"), FIRE("Fire"), LIGHTNING("Lightning");
	
	private String enumText;
	
	private SpellType(String text) {
		this.enumText = text;
	}
	
	@Override
	public String toString() {
		return enumText;
	}
}
