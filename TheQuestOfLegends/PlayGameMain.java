import java.util.Scanner;

public class PlayGameMain {
	
	public static void main(String[] args) {
		try (Scanner scan = new Scanner(System.in)) {  // using try with resources so scanner always gets closed even if game is ended abruptly
			GameDriver questGameDriver = new GameDriver(); // create an instance of GameDriver
			questGameDriver.executeGame(scan); // execute the game 
		}
	}
	
}
