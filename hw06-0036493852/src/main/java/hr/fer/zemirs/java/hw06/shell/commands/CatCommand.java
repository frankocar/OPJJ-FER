package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'cat' will print out the content of any given file
 * It can accept one or two arguments, first one being the path to the file to print and the second, optional, one being the desired charset to use 
 * It's possible to check available charsets using the 'charset' command 
 * Usage: 'cat [filepath] (charset)'
 * 
 * @author Franko Car
 *
 */
public class CatCommand implements ShellCommand {
	
	/** Command name */
	private static final String NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] parsedArguments = ArgumentParser.twoStringArguments(arguments, getCommandName());
		Path path = Paths.get(parsedArguments[0]);
		
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException("Argument must be a file");
		}
		
		Charset charset = null;
		try {
			if (parsedArguments[1] == null) {
				charset = Charset.defaultCharset();
			} else {
				charset = Charset.forName(parsedArguments[1]);
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Unsupported charset requested: " + ex.getMessage(), ex);
		}
		
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException ex) {
			throw new RuntimeException("File not readable using the desired charset: " + ex.getMessage(), ex);
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
		desc.add("Command 'cat' will print out the content of any given file");
		desc.add("It can accept one or two arguments, first one being the path to the file to print"
				+ " and the second, optional, one being the desired charset to use");
		desc.add("It's possible to check available charsets using the 'charset' command");
		desc.add("Usage: 'cat [filepath] (charset)'");
		return Collections.unmodifiableList(desc);
	}

}
