package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.crypto.Util;
import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'hexdump' will print out the hexadecimal representation of a given file and corresponding ASCII values 
 * It accepts a single argument, path of the file to print 
 * Usage: 'hexdump [file]'
 * 
 * @author Franko
 *
 */
public class HexdumpCommand implements ShellCommand {
	
	/** Command name */
	private static final String NAME = "hexdump";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = ArgumentParser.singlePathArgument(arguments, getCommandName());
		
		if (Files.isDirectory(path)) {
			throw new IllegalArgumentException("Given path must be a file");
		}
		
		hexdump(env, path);
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * A method that will dump the contents of a file in a required format
	 * 
	 * @param env Environment to use
	 * @param path Path of a file
	 */
	private void hexdump(Environment env, Path path) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buffer = new byte[16];
			int size = 0;
			int read;
			
			while ((read = is.read(buffer)) != -1) {
				StringBuilder sb = new StringBuilder();

				sb.append(String.format("%08x", size).toUpperCase());
				sb.append(": ");
				size+=16;
				
				String hex = Util.bytetohex(buffer).toUpperCase();
				for (int i = 0; i < hex.length(); i += 2) {
					if (i < read * 2) {
						sb.append(hex.charAt(i));
						sb.append(hex.charAt(i + 1));
					} else {
						sb.append("  ");
					}
					
					if (i == 14) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
				}
				sb.append("| ");

				for (int i = 0; i < buffer.length; i++) {
					if (buffer[i] < 32 || buffer[i] > 127) {
						buffer[i] = '.';
					}
				}
				
				sb.append(new String(buffer, 0 , read, "UTF-8"));
				env.writeln(sb.toString());
			}
		} catch (IOException ex) {
			throw new RuntimeException("File access error: " + ex.getMessage(), ex);
		}
		
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'hexdump' will print out the hexadecimal representation of a given file and corresponding ASCII values");
		desc.add("It accepts a single argument, path of the file to print");
		desc.add("Usage: 'hexdump [file]'");
		return Collections.unmodifiableList(desc);
	}

}
