package hr.fer.zemirs.java.hw06.shell;

import java.util.SortedMap;

import hr.fer.zemirs.java.hw06.shell.commands.ShellCommand;

/**
 * An interface defining shell environment.
 * 
 * @author Franko Car
 *
 */
public interface Environment {

	/**
	 * A method that will read user input from shell
	 * 
	 * @return String with data
	 * @throws ShellIOException Reading was unsuccessful
	 */
	String readLine() throws ShellIOException;

	/**
	 * A method that will print a string to the user of the shell
	 * 
	 * @param text Text to print
	 * @throws ShellIOException Writing was unsuccessful
	 */
	void write(String text) throws ShellIOException;

	/**
	 * A method that will print a string and a new line to the user of the shell
	 * 
	 * @param text Text to print
	 * @throws ShellIOException Writing was unsuccessful
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * A method that will return a map of shells available commands
	 * 
	 * @return SortedMap<String, ShellCommand> implemented commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * A method that will return the current multiline symbol.
	 * 
	 * @return Character current multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * A method to set new multiline symbol
	 * 
	 * @param symbol New symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * A method that will return the current prompt symbol.
	 * 
	 * @return Character current prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * A method to set new prompt symbol
	 * 
	 * @param symbol New symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * A method that will return the current morelines symbol.
	 * 
	 * @return Character current morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * A method to set new morelines symbol
	 * 
	 * @param symbol New symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
}
