/*
 * Controller initializes the players of the game 
 * and the game itself. Users are allowed to modify the 
 * number of cells they want printed out for the game.
 * Currently set to the default 3x3 Tic Tac Toe game board,
 * and winning length is also set to default of 3.
 */

public class Game {
	public static void main(String[] args) {
		
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		String [] message = {"It's a tie!", "Player X has won!", "Player O has won!"};
		System.out.println("Welcome to a game of Tic Tac Toe! \n" +
					"\n" +
					"How to play: \n" +
					"Please type in two numbers (ex. 1 2) corresponding to the row and column \n" +
					"where you would like to place your next move. Rows and columns are labeled \n" +
					"on the board for your convenience. Good luck! \n");
		System.out.println(message[Controller.play(3, 3, 3, p1, p2)]);
	}
}
