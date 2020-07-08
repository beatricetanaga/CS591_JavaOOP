
public enum CellType {
	COMMON("C"), MARKET("M"), INACCESSIBLE("X");
	
	private String enumTxt;
	
	private CellType(String txt) {
		this.enumTxt = txt;
	}
	
	@Override
	public String toString() {
		return this.enumTxt;
	}
}
