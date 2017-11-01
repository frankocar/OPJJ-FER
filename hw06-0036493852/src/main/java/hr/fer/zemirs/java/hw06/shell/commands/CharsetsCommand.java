package hr.fer.zemirs.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'charsets' will print out all available charsets
 * It doesn't require any arguments and will terminate if an argument is given
 * 
 * @author Franko Car
 *
 */
public class CharsetsCommand implements ShellCommand {

	/** Command name */
	private static final String NAME = "charsets";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			throw new IllegalArgumentException("Unsupported arguments for command 'charsets'. Try 'help charsets' for more information");
		}
		
		Charset.availableCharsets().forEach((k, v) -> env.writeln(k));
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'charsets' will print out all available charsets");
		desc.add("It doesn't require any arguments and will terminate if an argument is given");
		return Collections.unmodifiableList(desc);
	}

}
