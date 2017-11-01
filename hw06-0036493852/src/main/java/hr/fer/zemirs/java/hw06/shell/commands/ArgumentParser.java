package hr.fer.zemirs.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class containing static methods to parse arguments.
 * 
 * @author Franko Car
 *
 */
class ArgumentParser {

	/**
	 * A method that will take in a string, check if it contains a single valid argument and parse
	 * it as a path if possible.
	 * 
	 * @param arguments Input string
	 * @param name Name of a command calling the method
	 * @return Path parsed from string
	 * @throws IllegalArgumentException if the argument isn't valid
	 */
	static Path singlePathArgument(String arguments, String name) {
		Pattern pattern = Pattern.compile("^\\s*\"(.+)\"|(\\S+)\\s*$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(arguments);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Unsupported arguments for command '%s'. Try 'help %s' for more information", name, name));
		}
		
		Path path = matcher.group(1) == null ? Paths.get(matcher.group(0)) : Paths.get(checkEscape(matcher.group(1)));		
		
		return path;
	}
	
	/**
	 * A method that will parse a given string as two separate strings. If separate arguments
	 * are enclosed in quotation marks it will take them in as one even if they contain spaces.
	 * Arguments will be returned as an array of strings.
	 * 
	 * @param arguments Input string
	 * @param name Name of a command calling the method
	 * @return String[] Array of parsed arguments
	 * @throws IllegalArgumentException if the argument isn't valid
	 */
	static String[] twoStringArguments(String arguments, String name) {
		Pattern pattern = Pattern.compile("^\\s*(\"([^\"]+)\"|(\\S+))(\\s+(\"(.+)\"|(\\S+))?)?\\s*$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(arguments);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Unsupported arguments for command '%s'. Try 'help %s' for more information", name, name));
		}
		
		String[] args = new String[2];			
		args[0] = matcher.group(3) == null ? matcher.group(2) : checkEscape(matcher.group(3));
		args[1] = matcher.group(6) == null ? matcher.group(5) : checkEscape(matcher.group(6));
		
		return args;
		
	}
	
	/**
	 * A method that will check if a string contains valid escape sequences and replace them
	 * 
	 * @param string Input string
	 * @return Checked and changed(if necessary) string
	 */
	private static String checkEscape(String string) {
		if (string == null) {
			return null;
		}
		
		if (!string.contains("\\\\") && !string.contains("\\\"")) {
			return string;
		}
		
		char[] data = string.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < data.length - 1; i++) {
			if (data[i] == '\\') {
				if (data[i + 1] == '\\') continue;
				
				if (data[i + 1] == '\"') {
					i++;
				}				
			}			
			sb.append(data[i]);
		}
		
		return sb.toString();
	}
	
}
