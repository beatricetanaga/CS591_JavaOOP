import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parsed command
 * @author btanaga
 *
 */
public class Command {
	
	private CommandType type;
	private List<String> args;
	
	/**
	 * @param command
	 * @param args
	 */
	public Command(CommandType commandType, List<String> args) {
		this.type = commandType;
		this.args = args;
	}
	
	public static Command parseInput(String input) {
		if(input == null || input.trim().length() == 0) {
			return null;
		}
		
		CommandType type;
		List<String> argList = new ArrayList<>();
		
		// TODO - do the parsing here
		String[] toks = input.trim().toLowerCase().split("\\s+");
		
		switch(toks[0]) {
		case "m":
			type = CommandType.MOVE;
			break;
		case "i":
			type = CommandType.INVENTORY;
			break;
		case "d":
			type = CommandType.DISPLAY;
			break;
		case "e":
			type = CommandType.EQUIP;
			break;
		case "b":
			type = CommandType.BUY;
			break;
		case "s":
			type = CommandType.SELL;
			break;
		case "u":
			type = CommandType.USE;
			break;
		case "c":
			type = CommandType.CAST;
			break;
		case "q":
			type = CommandType.QUIT;
			break;
		default:
			return null;
		}
		
		// add the other tokens to the arg list
		for(int i = 1; i < toks.length; i++) {
			argList.add(toks[i]);
		}

		return new Command(type, argList);
	}

	/**
	 * @return the commands
	 */
	public CommandType getType() {
		return type;
	}

	/**
	 * @return the args
	 */
	public List<String> getArgs() {
		return args;
	}
}
