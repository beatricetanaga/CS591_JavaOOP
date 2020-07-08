/*
 * Game class initializes a new board object to be printed in the main class of controller.
 * Safety checks for whether the board is full and if there is a winner is also done here.
 * If currently no winner and board is not full, signals the next player for their turn.
 * 
 * 0 return -> Tie
 * 1 return -> Player X won
 * 2 return -> Player O won
 */

public class Controller {
	public static int play(int height, int width, int winLength, Player p1, Player p2) {
		
		Board b = new Board(height, width, winLength);
		System.out.println(b);
		
		while (!b.isFull()) {
			System.out.println("It's Player X's turn to play.");
			b.addChecker(1, p1.nextMove(b));
			System.out.println(b);
			
			if (b.hasWon(1)) return 1;
			
			if (b.isFull()) return 0;
			
			System.out.println("It's Player O's turn to play.");
			b.addChecker(2, p2.nextMove(b));
			System.out.println(b);
			
			if (b.hasWon(2)) return 2;
		}
		return 0;
	}
}
