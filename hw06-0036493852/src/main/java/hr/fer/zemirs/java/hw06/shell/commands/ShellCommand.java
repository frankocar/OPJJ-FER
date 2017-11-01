package hr.fer.zemirs.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Interface defining a command.
 * 
 * @author Franko Car
 *
 */
public interface ShellCommand {

	/**
	 * Executes a command in a given environment using given arguments.
	 * It will return the desired shell state after its execution.
	 * 
	 * @param env Environment in which command will be executed
	 * @param arguments Arguments for a command
	 * @return Desired shell status after the command executes
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of a command
	 * 
	 * @return Command name
	 */
	String getCommandName();

	/**
	 * A method that will return a list of strings containing the description of a command
	 * 
	 * @return List<String> command description
	 */
	List<String> getCommandDescription();

}
