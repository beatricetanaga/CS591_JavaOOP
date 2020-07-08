/*
 * class that controls the game of Order and Chaos.
 * The board for Order and Chaos is set to be a 6x6 board, and the win length is set to be 5.
 */

import java.util.Scanner;

public class OrderAndChaos {
	
	private Board board;
	private gameState currState;
	private Checker currChecker;
	
	private Role currPlayer;
	
	private static Scanner sc = new Scanner(System.in);
	private static Scanner p = new Scanner(System.in);
	private static Scanner c = new Scanner(System.in);
	
	
	public OrderAndChaos() {
		board = new Board(6, 6, 5);
		
		System.out.println("Welcome to a game of Order and Chaos! \n" +
				"\n" +
				"How to play: \n" +
				"You will first be asked to choose between the roles 'Oder' and 'Chaos'. \n" +
				"The player who has chosen 'Order' will proceed to play the first move. \n" +
				"Both players are allowed to place either a 'X' or a 'O' checker. \n" +
				"Order, your goal is to align 5 checkers vertically, horizontally, or diagonally within the board. \n" +
				"Chaos, your goal is to prevent Order from doing just that. \n" +
				"Good luck and have fun! \n");
		
		initGame();
		
		boolean validInput = false;
		System.out.print("Player, would you like to be Order or Chaos? \n" +
				"Enter 'o' for Order, and 'c' for Chaos. \n");

		do {
			char role = p.next().charAt(0);

			if (role == 'o') {
				System.out.print("You have chosen the role of Order, you may go first. \n");
				validInput = true;
			} 
			else if (role == 'c') {
				System.out.print("You have chosen the role of Chaos, please allow Order to place the first checker. \n");
				validInput = true;
			} else {
				System.out.println("Please re-enter your desired role.");
			}
		} while(!validInput);
		
		do {
			board.build();
			playerMove(currPlayer);
			updateGame(currChecker);
			
			if (currState == gameState.orderHasWon) {
				board.build();
				System.out.println("Order has won!");
			}
			else if (currState == gameState.chaosHasWon) {
				board.build();
				System.out.println("Chaos has won!");
			}
			
			currPlayer = (currPlayer == Role.Order) ? Role.Chaos : Role.Order;
		} while (currState == gameState.inProgress);
	}
	
	public void initGame() {
		board.init();
		
		currPlayer = Role.Order;
		currChecker = Checker.EMPTY;
		currState = gameState.inProgress;
	}
	
	public void playerMove(Role currPlayer) {
		boolean validInput = false;

		do {
			if (currPlayer == Role.Order) {
				System.out.println("Order, what's your move?");
			}
			else {
				System.out.println("Chaos, what's your move?");
			}
			int row = sc.nextInt();
			int col = sc.nextInt();
			
			if (row >= 0 && row < board.getHeight() && col >= 0 && col < board.getWidth() && board.cells[row][col].content == Checker.EMPTY) {
				
				System.out.print("Would you like to place an 'X' or an 'O' checker?");
				do {
					char checker = c.next().charAt(0);
					if (checker == 'X') {
						validInput = true;
						currChecker = Checker.X;
					} else if (checker == 'O') {
						validInput = true;
						currChecker = Checker.O;
					} else {
						System.out.println("Please enter either 'X' or 'O'.");
					}
				} while (!validInput);
				
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
			currState = gameState.orderHasWon;
		}
		else if (board.isFull()) {
			currState = gameState.chaosHasWon;
		}
	}

}
