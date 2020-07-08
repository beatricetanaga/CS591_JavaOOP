/*
 * The main class to initialize the games. Player will be given the choice between the list of 
 * available games to be played.
 */

import java.util.Scanner;

public class Game {
	
	private static Scanner sc = new Scanner(System.in);
	private static Scanner r = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean play = true;
		while(play) {
			System.out.print("Welcome! Would you like to play a game of . . . \n" +
					"(1) Tic Tac Toe \n" +
					"(2) Order and Chaos \n \n" +
					"Please enter your choice :");

			int choice = sc.nextInt();

			if (choice == 1) {
				new TicTacToe();
			} else new OrderAndChaos();
			
			System.out.println();
			System.out.println("Would you like to play another round?");
			
			String reset = r.next().toLowerCase();
			while (!(reset.equals( "y")|| reset.equals("n"))) {
				System.out.println("invalid input please enter 'y' or 'n'.");
				
				reset = r.next().toLowerCase();
				System.out.println(reset);
			}
			
			
			if (reset.contentEquals("n")) {
				System.out.println("Goodbye.");
				play = false;
			}
			else continue;
		} 
	}
	
}
