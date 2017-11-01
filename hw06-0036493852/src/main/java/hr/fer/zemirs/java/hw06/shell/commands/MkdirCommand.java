package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'mkdir' will create new folder(s) 
 * It requires a single argument, a path to the folder structure to be created 
 * Usage: 'mkdir [directory]'
 * 
 * @author Franko Car
 *
 */
public class MkdirCommand implements ShellCommand {

	/** Command name */
	private static final String NAME = "mkdir";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = ArgumentParser.singlePathArgument(arguments, getCommandName());
		
		try {
			Files.createDirectories(path);
			env.writeln("Directory created");
		} catch (IOException ex) {
			throw new RuntimeException("Unable to create directory: " + ex.getMessage(), ex);
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
		desc.add("Command 'mkdir' will create new folder(s)");
		desc.add("It requires a single argument, a path to the folder structure to be created");
		desc.add("Usage: 'mkdir [directory]'");
		return Collections.unmodifiableList(desc);
	}

}
