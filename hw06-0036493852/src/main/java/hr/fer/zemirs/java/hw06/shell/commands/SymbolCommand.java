package hr.fer.zemirs.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'symbol' allows user to change shell symbols 
 * It requires 1 or 2 arguments, fist one being the symbol type, and the second one new symbol 
 * If only a type is given, the current symbol will be printed, and if a new symbol is given the symbol will be changed 
 * Usage: 'symbol [PROMPT|MORELINES|MULTILINES] (new symbol)'
 * 
 * @author Franko Car
 *
 */
public class SymbolCommand implements ShellCommand {

	/** Command name */
	private static final String NAME = "symbol";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split("\\s+");
		
		if (args.length == 1) {
			return returnCurrent(env, args[0].toUpperCase());
		}
		
		if (args.length != 2 || (args.length >= 2 && args[1].length() > 1)) {
			throw new IllegalArgumentException("Unsupported arguments for command 'symbol'. Try 'help symbol' for more information");
		}
		
		char newSymbol = args[1].charAt(0);		
		return changeCurrent(env, args[0].toUpperCase(), newSymbol);
	}
	
	/**
	 * A method that will change the current symbol
	 * 
	 * @param env Environment to use
	 * @param argument Symbol to change
	 * @param newSymbol New symbol
	 * @return Status of shell
	 */
	private ShellStatus changeCurrent(Environment env, String argument, char newSymbol) {
		switch (argument) {
		case "PROMPT":
			env.writeln(String.format("Symbol for PROMPT changed from %c to %c", env.getPromptSymbol(), newSymbol));
			env.setPromptSymbol(newSymbol);
			break;
		case "MORELINES":
			env.writeln(String.format("Symbol for MORELINES changed from %c to %c", env.getMorelinesSymbol(), newSymbol));
			env.setMorelinesSymbol(newSymbol);
			break;
		case "MULTILINES":
			env.writeln(String.format("Symbol for MULTILINES changed from %c to %c", env.getMultilineSymbol(), newSymbol));
			env.setMultilineSymbol(newSymbol);
			break;
		default:
			throw new IllegalArgumentException("Unknown symbol type");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * A method that will print the current symbol
	 * 
	 * @param env Environment to use
	 * @param argument Symbol to print
	 * @return Status of shell
	 */
	private ShellStatus returnCurrent(Environment env, String argument) {
		switch (argument) {
		case "PROMPT":
			env.writeln(String.format("Symbol for PROMPT is %c", env.getPromptSymbol()));
			break;
		case "MORELINES":
			env.writeln(String.format("Symbol for MORELINES is %c", env.getMorelinesSymbol()));
			break;
		case "MULTILINES":
			env.writeln(String.format("Symbol for MULTILINES is %c", env.getMultilineSymbol()));
			break;
		default:
			throw new IllegalArgumentException("Unknown symbol type");
		}	
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'symbol' allows user to change shell symbols");
		desc.add("It requires 1 or 2 arguments, fist one being the symbol type, and the second one new symbol");
		desc.add("If only a type is given, the current symbol will be printed, and if a new symbol is given the symbol will be changed");
		desc.add("Usage: 'symbol [PROMPT|MORELINES|MULTILINES] (new symbol)'");
		return Collections.unmodifiableList(desc);
	}
	
}
