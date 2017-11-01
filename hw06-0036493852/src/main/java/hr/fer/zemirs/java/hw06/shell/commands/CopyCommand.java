package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'copy' will copy a file given as a first argument to the location specified by the second argument 
 * First argument must be a file, while the second one may be a folder. If a folder is given, filename of
 * a new file will remain the same. This command won't create a new folder if it doesn't exists, so 
 * make sure you create it first using 'mkdir' command 
 * If a file with the same path as the second argument already exist, a prompt will be shown to confirm the overwrite
 * Usage: 'copy [input file] [output file]'
 * 
 * @author Franko Car
 *
 */
public class CopyCommand implements ShellCommand {
	
	/** Command name */
	private static final String NAME = "copy";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] args = ArgumentParser.twoStringArguments(arguments, getCommandName());
		
		if (args[0] == null || args[1] == null) {
			throw new IllegalArgumentException("Invalid arguments provided, please refer to 'help copy'.");
		}
		
		Path input = null;
		Path output = null;
		try {
			input = Paths.get(args[0]);
			output = Paths.get(args[1]);
		} catch (InvalidPathException ex) {
			throw new IllegalArgumentException("Given path is invalid: " + ex.getMessage(), ex);
		}
		
		if (Files.isDirectory(input)) {
			throw new IllegalArgumentException("Invalid arguments provided, please refer to 'help copy'.");
		}
		
		if (Files.isDirectory(output)) {
			output = Paths.get(output.toAbsolutePath().toString(), input.getFileName().toString());
		}
		
		if (Files.exists(output)) {
			if (!overwrite(env)) {
				return ShellStatus.CONTINUE;
			}
		}
		
		try (
			InputStream is = new BufferedInputStream(Files.newInputStream(input));
			OutputStream os = new BufferedOutputStream(Files.newOutputStream(output));
		) {
			byte[] buffer = new byte[4096];
			int length;
			
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			
			env.writeln("Copy successful");
		} catch (IOException ex) {
			throw new RuntimeException("Copy failed: " + ex.getMessage(), ex);
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * A method that will check if overwrite is wanted
	 * 
	 * @param env Environment to use
	 * @return True if overwrite is wanted, false otherwise
	 */
	private boolean overwrite(Environment env) {
		env.writeln("A file at a given destination already exists.");
		env.writeln("Do you want to owerwrite it? (YES/NO)");
		String input = env.readLine();
		if (input.toLowerCase().startsWith("y")) {
			return true;
		} else if (input.toLowerCase().startsWith("n")) {
			return false;
		}
		
		throw new IllegalArgumentException("Unknown input, please try again");
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'copy' will copy a file given as a first argument to the location specified by the second argument");
		desc.add("First argument must be a file, while the second one may be a folder. If a folder is given, filename of"
				+ " a new file will remain the same. This command won't create a new folder if it doesn't exists, so "
				+ "make sure you create it first using 'mkdir' command");
		desc.add("If a file with the same path as the second argument already exist, a prompt will be shown to confirm the overwrite");
		desc.add("Usage: 'copy [input file] [output file]'");
		return Collections.unmodifiableList(desc);
	}

}
