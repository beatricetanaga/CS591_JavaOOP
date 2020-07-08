/*
 * Board class is responsible for creating the Tic Tac Toe board 
 * display and printing out new input (aka. moves) made by the 
 * player. Safety checks for empty cells and complete row, column 
 * and diagonal winners are also done in this class 
 */
public class Board {
	
	public static final char [] sym = {' ', 'X', 'O'};
	
	private int[][] board;
	private int height, width;
	private int winLength;
	
	public Board (int h, int w, int winLen) {
		height = h;
		width = w;
		winLength = winLen;
		board = new int [height][width];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void addChecker (int type, int[] cell) {
		assert (type == 1 || type == 2);
		board[cell[0]][cell[1]] = type;
	}
	
	public boolean canAddTo (int row, int column) {
		return row>=0 && column>=0 && board[row][column]==0;
	}
	
	public boolean isFull () {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (board[row][col]==0) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean verticalWin (int type) {
		if(height < winLength) return false;
		for (int j = 0; j<width; j++) {
			int count = 0;
			for (int i = 0; i<height; i++) {
				if (board[i][j]==type) {
					count ++;
					if (count == winLength) return true;
				}
				else if (board[i][j]==0) break;
				else count = 0;
			}
		}
		return false;
	}
	
	private boolean horizontalWin (int type) {
		if(width < winLength) return false;
		for (int i = 0; i<height; i++) {
			int count = 0;
			for (int j = 0; j<width; j++) {
				if (board[i][j]==type) {
					count++;
					if (count == winLength) return true;
				}
				else count = 0;
			}
		}
		return false;
	}
	
	private boolean nwDiagonalWin (int type) {
		if(height < winLength || width < winLength) return false;
		int startRow = 0;
		int startCol = winLength-1;
		/* try diagonals that start anywhere on the bottom edge
		 * or on the right edge and are long enough 
		 */
		while (startRow <= height-winLength) {
			int count = 0;
			for (int j = 0; startRow+j<height && startCol-j>=0; j++) {
				if (board[startRow+j][startCol-j] == type) {
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
	
	private boolean neDiagonalWin (int type) {
		if(height < winLength || width < winLength) return false;
		int startRow = height-winLength;
		int startCol = 0;
		/*try diagonals that start anywhere on the left edge
		 * or on the bottom edge and are long enough
		 */
		while (startCol <= width-winLength) {
			int count = 0;
			for (int j = 0; startRow+j<height && startCol+j<width; j++) {
				if (board[startRow+j][startCol+j] == type) {
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
	
	public boolean hasWon (int type) {
		return verticalWin (type) || horizontalWin(type) || neDiagonalWin(type) || nwDiagonalWin(type);
	}
	
	public String toString() {
		String s = "";
		s += "  ";
		for (int j = 0; j<width; j++) {
			s += "--";
		}
		s+="-\n";
		for (int i = height-1; i>=0; i--) {
			s += i+" ";
			for (int j = 0; j<width; j++) {
				s += "|"+sym[board[i][j]];
			}
			s+="|\n";
			s += "  ";
			for (int j = 0; j<width; j++) {
				s += "--";
			}
			s+="-\n";
		}
		s += "  ";
		for (int j = 0; j<width; j++) {
			s += " "+j%10;
		}
		s+="\n";
		return s;
	}
}
