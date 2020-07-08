// A class that represents a lane on the board (top lane, mid lane, and bot lane) 
public class Lane {
	public int numRows; 
	public int numCols; 
	public Tile[][] grid; 
	
	public Lane(int rows, int cols) {
		grid = new Tile[rows][cols]; 
	}

}
