/*
 * class that controls the game of Tic Tac Toe.
 * The board for Tic Tac Toe is set to the default of 3x3 board, and the win length is set to be 3.
 * You can modify the board size, and win length during the initializing of the board.
 */

import java.util.Scanner;

public class TicTacToe {
	private Board board;
	private gameState currState;
	private Checker currPlayer;
	//
	private static Scanner sc = new Scanner(System.in);
	
	public TicTacToe() {
		/* Modify board size and win length as desired in the line below; Board(height, width, win length)*/
		board = new Board(3, 3, 3);
		
		initGame();
		
		System.out.println("Welcome to a game of Tic Tac Toe! \n" +
				"\n" +
				"How to play: \n" +
				"Please type in two numbers (ex. 1 2) corresponding to the row and column \n" +
				"where you would like to place your next move. Rows and columns are labeled \n" +
				"on the board for your convenience. Good luck and have fun! \n");
		
		do {
			board.build();
			playerMove(currPlayer);
			updateGame(currPlayer);
			
			if (currState == gameState.xHasWon) {
				board.build();
				System.out.println("Player X has won!");
			}
			else if (currState == gameState.oHasWon) {
				board.build();
				System.out.println("Player O has won!");
			}
			else if (currState == gameState.tie) {
				board.build();
				System.out.println("It's a tie!");
			}
			
			currPlayer = (currPlayer == Checker.X) ? Checker.O : Checker.X;
		} while (currState == gameState.inProgress);
	}
	
	public void initGame() {
		board.init();
		currPlayer = Checker.X;
		currState = gameState.inProgress;
	}
	
	public void playerMove(Checker currChecker) {
		boolean validInput = false;
		
		do {
			if (currChecker == Checker.X) {
				System.out.println("Player X, what's your move?");
			}
			else {
				System.out.println("Player O, what's your move?");
			}
			int row = sc.nextInt();
			int col = sc.nextInt();
			
			if (row >= 0 && row < board.getHeight() && col >= 0 && col < board.getWidth() && board.cells[row][col].content == Checker.EMPTY) {
				board.cells[row][col].content = currChecker;
				board.currRow = row;
				board.currCol = col;
				
				validInput = true;
			} else {
				System.out.println("Illegal move, please try again. Please key in a row followed by a column (ex. '1 2').");
			}
		} while (!validInput);
	}
	
	public void updateGame(Checker currChecker) {
		if (board.hasWon(currChecker)) {
			currState = (currChecker == Checker.X) ? gameState.xHasWon : gameState.oHasWon;
		}
		else if (board.isFull()) {
			currState = gameState.tie;
		}
	}
}
