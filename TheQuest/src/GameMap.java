
import java.util.Random;

public class GameMap {

	private int height;
	private int width;
	private Cell[][] gameArea;
	private Random rand;
	
	public GameMap(int h, int w) {
		this.rand = new Random();
		
		this.height = h;
		this.width = w;
		gameArea = new Cell[h][w];
		

		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				gameArea[row][col] = new Cell();
			}
		}
		
		init();
	}
	
	private void init() {
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				double roll = rand.nextDouble();
				
				if(roll <= 0.2) {
					gameArea[row][col].market();; 
				}
				
				else if(roll <= 0.85) {
					gameArea[row][col].common();
				}
				
				else {
					gameArea[row][col].nonaccessible();
				}
			}
		}
	}

	public void printMap(int x, int y) {
		
		System.out.print("\n    ");
		for (int col = 0 ; col< width; ++col) {
			System.out.print("+---");
		}
		System.out.print("+ \n");
		
		for (int yPos = height -1; yPos >= 0; yPos--) {
			System.out.printf("%3d |", yPos);
			for (int xPos = 0; xPos < width; ++xPos) {
				if(xPos == x && yPos == y) {
					 System.out.printf(" * ");
				} else {
				 System.out.printf(" %s ", gameArea[yPos][xPos]);
				}
				if (xPos < width) {
					System.out.print("|");
				}
			}
			System.out.println();

			System.out.print("    ");
			if (yPos != 0) {
				for (int col = 0 ; col< width; ++col) {
					System.out.print("+---");
				}
				System.out.print("+ \n");
			}
		}
		for (int col = 0 ; col< width; ++col) {
			System.out.print("+---");
		}
		System.out.print("+\n");
		
		// Column numbers
		System.out.print("    ");
		for (int col = 0 ; col< width; ++col) {
			System.out.printf("%3d ", col);
		}
		
		System.out.println();
	}

	
	public boolean isMarket(int x, int y) {
		Cell c = gameArea[y][x];
		return c.getType() == CellType.MARKET;
	}

	public boolean isCommon(int x, int y) {
		Cell c = gameArea[y][x];
		return c.getType() == CellType.COMMON;
	}

	public boolean isAccessable(int x, int y) {
		Cell c = gameArea[y][x];
		return c.getType() != CellType.INACCESSIBLE;
	}
	
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
}
