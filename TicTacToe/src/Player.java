/*
 * Player class responsible for taking and 
 * ensuring a valid user input before returning 
 * for game class to execute the next move 
 */

import java.util.Scanner;

public class Player extends Controller{
	
	Scanner sc;
	
	/* 1 for "X", 2 for "O" */
	protected int type; 
	public Player (int t) {
		type = t;
		sc = new Scanner(System.in);
	}
	
	int[] nextMove(Board board) {
		System.out.print("Player " + Board.sym[type] + ", what's your move?");
		int[] move = new int[2];
		
		do {
			move[0] = sc.nextInt();
			move[1] = sc.nextInt();
			while (!board.canAddTo(move[0], move[1])) {
				System.out.println("Illegal move, please try again!");
				move[0] = sc.nextInt();
				move[1] = sc.nextInt();
			}
		}
		while (!board.canAddTo(move[0], move[1]));
		return move;
	}
}
