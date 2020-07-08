import java.util.*; 
public class QuestMap {
	private int numRows; 
	private int numCols; 
	private Tile[][] grid; 
	private Lane topLane; 
	private Lane midLane; 
	private Lane botLane; 
	private int laneWidth; // width of each lane, it = 2 in this game
	
	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public Tile[][] getGrid() {
		return grid;
	}

	public void setGrid(Tile[][] grid) {
		this.grid = grid;
	}
	
	// constructor 
	public QuestMap(int rows, int cols, int laneWidth) {
		this.numRows = rows; 
		this.numCols = cols; 
		this.laneWidth = laneWidth;
		
		this.grid = new Tile[numRows][numCols];
		this.topLane = new Lane(numRows, laneWidth); 
		this.topLane.grid = new Tile[numRows][laneWidth]; 
		
		this.midLane = new Lane(numRows, laneWidth); 
		this.midLane.grid = new Tile[numRows][laneWidth];
		
		this.botLane = new Lane(numRows, laneWidth); 
		this.botLane.grid = new Tile[numRows][laneWidth]; 
		
	}
	
	// method that initializes the grid of the map 
	public void createGrid() {
		// set the cells of all of the lanes 
		createLane(topLane); 
		createLane(midLane); 
		createLane(botLane); 
		
		// set all the lanes and blockedTiles into the grid 
		for (int i = 0; i < numRows; i++) {
			// set topLane in the grid 
			for (int j = 0; j < laneWidth; j++) {
				grid[i][j] = topLane.grid[i][j]; 
			}
			
			// set blockedTiles between topLane and midLane 
			grid[i][laneWidth] = new BlockedTile(); 
			
			// set midLane in the grid 
			for (int j = laneWidth + 1; j < laneWidth + 1 + laneWidth; j++) {
				grid[i][j] = midLane.grid[i][j-(laneWidth + 1)]; 
			}
			
			// set blockedTiles between midLane and botLane 
			grid[i][laneWidth*2+1] = new BlockedTile(); 
			
			// set botLane in the grid
			for (int j = laneWidth*2 + 2; j < laneWidth*2 + 2 + laneWidth; j++) {
				grid[i][j] = botLane.grid[i][j-(laneWidth*2 + 2)]; 
			}
		}
	}
	
	// method that sets the cells of a lane 
	public void createLane(Lane lane) {
		// set the Nexus cells
		for (int j = 0; j < laneWidth; j++) {
			lane.grid[0][j] = new MonsterNexusTile(); // set top row to Monster Nexus
			lane.grid[numRows-1][j] = new HeroNexusTile(); // set bottom row to Hero Nexus
		}
		
		// set the remaining cells in each lane to random types of cells
		// 55% chance of being a Plain cell, 15% Bush, 15% Koulou, 15% Cave 
		for (int i = 1; i < numRows - 1; i++) {
			for (int j = 0; j < laneWidth; j++) {
				int randomInteger = (int)(100.0 * Math.random());
				if (randomInteger < 55) {
					lane.grid[i][j] = new PlainTile(); 
				} else if (randomInteger < 70) {
					lane.grid[i][j] = new BushTile(); 
				} else if (randomInteger < 85) {
					lane.grid[i][j] = new KoulouTile(); 
				} else {
					lane.grid[i][j] = new CaveTile(); 
				}
			}
		}
	}
	
	// method that sets the starting position of each hero in their nexus 
	public void setStartingPosition(ArrayList<Hero> heroes) {
		int nexusCol = 0; 
		for (Hero h : heroes) {
			grid[numRows-1][nexusCol].setHeroLabel(h.getHeroLabel());
			h.setRowPosition(numRows-1);
			h.setColPosition(nexusCol);
			nexusCol += 3; 
		}
	}
	
	// method that prints the map 
	public void printQuestMap() {
		for (int i = 0; i < numRows; i++) {
			System.out.print("   ");
			for (int j = 0; j < numCols; j++) {
				System.out.print(grid[i][j].getBorderLabel() + " - " + grid[i][j].getBorderLabel() + " - " + grid[i][j].getBorderLabel() + "  ");
			}
			System.out.println(); 
			System.out.print(i + "  "); 
			for (int j = 0; j < numCols; j++) {
				System.out.print("| " + grid[i][j].getHeroLabel() + " " + grid[i][j].getMonsterLabel() + " |" + "  "); 
			}
			System.out.println(); 
			System.out.print("   ");
			for (int j = 0; j < numCols; j++) {
				System.out.print(grid[i][j].getBorderLabel() + " - " + grid[i][j].getBorderLabel() + " - " + grid[i][j].getBorderLabel() + "  ");
			}
			System.out.println();
			System.out.println(); 
		}
		
		System.out.print("  "); 
		for (int j = 0; j < numCols; j++) {
			System.out.print("     " + j + "     "); 
		}
		System.out.println(); 
		System.out.println(); 
	}

}
