
public class Cell {
	
	private CellType type;
	
	public Cell () {
	}
	
	/**
	 * @return the type
	 */
	public CellType getType() {
		return type;
	}

	public void market() {
		type = CellType.MARKET;
	}
	
	public void common() {
		type = CellType.COMMON;
	}
	
	public void nonaccessible() {
		type = CellType.INACCESSIBLE;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
