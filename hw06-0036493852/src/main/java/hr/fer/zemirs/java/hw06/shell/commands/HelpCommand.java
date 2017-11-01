package hr.fer.zemirs.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'help' will print out a description of a command given as an argument 
 * If there aren't any arguments or an unrecognized command is given, a list of supported commands will be printed 
 * Usage: 'help [command name]'
 * 		
 * @author Franko Car
 *
 */
public class HelpCommand implements ShellCommand {
	
	/** Command name */
	private static final String NAME = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> commands = env.commands();
		arguments = arguments.trim();
		
		if (commands.containsKey(arguments)) {
			commands.get(arguments).getCommandDescription().forEach(env::writeln);
		} else {
			env.writeln("Unrecognized command!");
			env.writeln("Existing commands: ");
			commands.forEach((k, v) -> env.writeln(k));
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
		desc.add("Command 'help' will print out a description of a command given as an argument");
		desc.add("If there aren't any arguments or an unrecognized command is given, a list of supported commands will be printed");
		desc.add("Usage: 'help [command name]");
		return Collections.unmodifiableList(desc);
	}

}
