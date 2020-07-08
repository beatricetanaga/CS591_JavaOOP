/*
 * Board class is responsible for creating the board 
 * display of each game. Safety checks for empty cells and complete 
 * row, column and diagonal winners are also done in this class.
 */

public class Board {
	
	public int height;
	public int width;
	public int winLength;
	
	Cell[][] cells;
	
	int currRow, currCol;
	
	public void init() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				cells[row][col].clear();
			}
		}
	}
	
	public Board(int h, int w, int winLen) {
		height = h;
		width =w;
		cells = new Cell[h][w];
		winLength = winLen;
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				cells[row][col] = new Cell(row,col);
			}
		}
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isFull () {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (cells[row][col].content == Checker.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean verticalWin (Checker currChecker) {
		if(height < winLength) return false;
		for (int j = 0; j<width; j++) {
			int count = 0;
			for (int i = 0; i<height; i++) {
				if (cells[i][j].content == currChecker) {
					count ++;
					if (count == winLength) return true;
				}
				else if (cells[i][j].content == Checker.EMPTY) break;
				else count = 0;
			}
		}
		return false;
	}
	
	public boolean horizontalWin (Checker currChecker) {
		if(width < winLength) return false;
		for (int i = 0; i<height; i++) {
			int count = 0;
			for (int j = 0; j<width; j++) {
				if (cells[i][j].content == currChecker) {
					count++;
					if (count == winLength) return true;
				}
				else count = 0;
			}
		}
		return false;
	}
	
	public boolean nwDiagonalWin (Checker currChecker) {
		if(height < winLength || width < winLength) return false;
		int startRow = 0;
		int startCol = winLength-1;
		/* try diagonals that start anywhere on the bottom edge
		 * or on the right edge and are long enough 
		 */
		while (startRow <= height-winLength) {
			int count = 0;
			for (int j = 0; startRow+j<height && startCol-j>=0; j++) {
				if (cells[startRow+j][startCol-j].content == currChecker) {
					count++;
					if (count == winLength) return true;
				}
				else count = 0;
			}
			if (startCol==width-1) startRow++;
			else startCol++;
		}
		return false;
	}
	
	public boolean neDiagonalWin (Checker currChecker) {
		if(height < winLength || width < winLength) return false;
		int startRow = height-winLength;
		int startCol = 0;
		/*try diagonals that start anywhere on the left edge
		 * or on the bottom edge and are long enough
		 */
		while (startCol <= width-winLength) {
			int count = 0;
			for (int j = 0; startRow+j<height && startCol+j<width; j++) {
				if (cells[startRow+j][startCol+j].content == currChecker) {
					count++;
					if (count == winLength) return true;
				}
				else count = 0;
			}
			if (startRow==0) startCol++;
			else startRow--;
		}
		return false;
	}
	
	public boolean hasWon (Checker currChecker) {
		return verticalWin (currChecker) || horizontalWin(currChecker) || neDiagonalWin(currChecker) || nwDiagonalWin(currChecker);
	}
	
	public void build() {
		System.out.print("  ");
		for (int col = 0 ; col< width; ++col) {
			System.out.printf("  %d ", col);
		}
		System.out.print("\n  ");
		for (int col = 0 ; col< width; ++col) {
			System.out.print("+---");
		}
		System.out.print("+ \n");
		for (int row = 0; row < height; ++row) {
			System.out.print(row + " |");
			for (int col = 0; col < width; ++col) {
				cells[row][col].fill();
				if (col < width) {
					System.out.print("|");
				}
			}
				System.out.println();
				System.out.print("  ");
				if (row < height-1) {
					for (int col = 0 ; col< width; ++col) {
						System.out.print("+---");
					}
					System.out.print("+ \n");
				}
		}
		for (int col = 0 ; col< width; ++col) {
			System.out.print("+---");
		}
		System.out.print("+ \n \n");
	}

}
