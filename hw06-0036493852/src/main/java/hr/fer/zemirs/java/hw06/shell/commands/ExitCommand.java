package hr.fer.zemirs.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'exit' will terminate the shell 
 * No argument are required and if any are given user will be returned to the shell
 * 
 * @author Franko Car
 *
 */
public class ExitCommand implements ShellCommand {

	/** Command name */
	private static final String NAME = "exit";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			throw new IllegalArgumentException("Unsupported arguments for command 'exit'. Try 'help exit' for more information");
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'exit' will terminate the shell");
		desc.add("No argument are required and if any are given user will be returned to the shell");
		return Collections.unmodifiableList(desc);
	}

}
