import java.util.Scanner;
//UtilityHelper is a class only used to ensure that the user inputs an integer when needed
//It is called when the user is asked to enter a number of heroes, an item ID, or a hero ID
public class UtilityHelper {

	public static int getIntFromScanner(Scanner scan) {
		String s = scan.nextLine();
		Integer i;
		do {
			try {
				i = Integer.parseInt(s);
				break;
			} catch (Exception e) {
				System.out.print("Please enter a number: ");
				s = scan.nextLine();
			}
		} while (true);

		return i;
	}
	
}

