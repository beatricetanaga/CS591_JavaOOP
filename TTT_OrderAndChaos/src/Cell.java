/*
 * This class builds each individual cell created in the Board class for each game.
 */

public class Cell {
	
	Checker content;
	
	int row, col;
	
	public Cell (int row, int col) {
		this.row = row;
		this.col = col;
		
		clear();
	}
	
	public void clear() {
		content = Checker.EMPTY;
	}
	
	public void fill() {
		switch (content) {
			case X: System.out.print(" X ");
			break;
			case O: System.out.print(" O ");
			break;
			case EMPTY: System.out.print("   ");
			break;
		}
	}
}
