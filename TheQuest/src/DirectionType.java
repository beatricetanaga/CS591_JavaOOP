
public enum DirectionType {
	NORTH("north"), SOUTH("south"), EAST("east"), WEST("west");
	
	private String enumTxt;
	
	private DirectionType(String str) {
		this.enumTxt = str;
	}
	
	@Override
	public String toString() {
		return this.enumTxt;
	}
	
	/**
	 * Parses a String and returns a DirectionType.
	 * @param str
	 * @return
	 */
	public static DirectionType toDirection(String str) {
		switch(str) {
			case "n":
				return DirectionType.NORTH;
			case "north":
				return DirectionType.NORTH;
			case "e":
				return DirectionType.EAST;
			case "east":
				return DirectionType.EAST;
			case "s":
				return DirectionType.SOUTH;
			case "south":
				return DirectionType.SOUTH;
			case "w":
				return DirectionType.WEST;
			case "west":
				return DirectionType.WEST;
			default:
				return null;
		}
	}
}
