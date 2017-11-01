package hr.fer.zemirs.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemirs.java.hw06.shell.commands.CatCommand;
import hr.fer.zemirs.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemirs.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemirs.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemirs.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemirs.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemirs.java.hw06.shell.commands.LsCommand;
import hr.fer.zemirs.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemirs.java.hw06.shell.commands.ShellCommand;
import hr.fer.zemirs.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemirs.java.hw06.shell.commands.TreeCommand;

/**
 * Shell emulator that implements basic commands.
 * Implemented commands are:
 * 		cat
 * 		charsets
 * 		copy
 * 		exit
 * 		help
 * 		hexdump
 * 		ls
 * 		mkdir
 * 		symbol
 * 		tree
 * 
 * It's possible to change default symbols by using the 'symbol' command.
 * To exit the shell use 'exit' command.
 * Behavior of other commands can be accessed by using 'help [command name]'.
 * 
 * @author Franko Car
 *
 */
public class MyShell {
	
	/**
	 * Main methods
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		Environment environment = null;
		try {
			environment = new EnvironmentImpl();
			environment.writeln("Welcome to MyShell v 1.0");
		} catch (ShellIOException ex) {
			System.out.println("IO error, terminating");
			System.exit(1);
		}
		SortedMap<String, ShellCommand> commands = environment.commands();
		ShellStatus currentStatus = ShellStatus.CONTINUE;
		
		
		while (currentStatus == ShellStatus.CONTINUE) {
			
			try {
				environment.write(environment.getPromptSymbol() + " ");
				String input = environment.readLine();
				
				while (input.endsWith(environment.getMorelinesSymbol().toString())) {
					environment.write(environment.getMultilineSymbol() + " ");
					input = input.substring(0, input.length() - 1) + " " + environment.readLine();
				}
				
				String[] inputSplit = input.split("\\s+", 2);
				
				String commandName = inputSplit[0].trim();
				String commandArguments = inputSplit.length == 2 ? inputSplit[1].trim() : "";
	
				
				
				ShellCommand command = commands.get(commandName);
				
				if (command == null) {
					environment.writeln("Unknown command: " + commandName);
					environment.writeln("Try running 'help' for list of available commands");
					continue;
				}
				
				currentStatus = command.executeCommand(environment, commandArguments);			
			} catch (ShellIOException ex) {
				System.out.println("IO error, terminating");
				System.exit(1);
			} catch (RuntimeException ex) {
				environment.writeln(ex.getMessage());
				continue;
			}
		}
		
		environment.writeln("Shell terminated. Goodbye!");
	}
	
	
	

	/**
	 * Implementation of the Environment interface for use in {@link MyShell}
	 * 
	 * @author FrankoCar
	 *
	 */
	private static class EnvironmentImpl implements Environment {

		/** System input scanner */
		private Scanner sc = new Scanner(System.in);
		/** Multiline symbol */
		private char multilineSymbol;
		/** Prompt symbol */
		private char promptSymbol;
		/** Morelines symbol */
		private char morelinesSymbol;
		
		/** A map containing all commands mapped to their name */
		private static SortedMap<String, ShellCommand> map = new TreeMap<>((k1, k2) -> k1.compareTo(k2));
		
		/** An array of commands to be added in map */
		private static ShellCommand[] commands = {
			new CatCommand(),
			new CharsetsCommand(),
			new CopyCommand(),
			new HexdumpCommand(),
			new LsCommand(),
			new MkdirCommand(),
			new TreeCommand(),
			new SymbolCommand(),
			new ExitCommand(),
			new HelpCommand()
		};
		
		/**
		 * Default constructor
		 */
		public EnvironmentImpl() {
			multilineSymbol = '|';
			promptSymbol = '>';
			morelinesSymbol = '\\';
			
			for (ShellCommand command : commands) {
				map.put(command.getCommandName(), command);
			}
		}
		
		@Override
		public String readLine() throws ShellIOException {
			String line;
			
			try {
				line = sc.nextLine();
			} catch (NoSuchElementException | IllegalStateException ex) {
				throw new ShellIOException("Unable to read line", ex);
			}
			
			return line;
		}

		@Override
		public void write(String text) throws ShellIOException {
			try {
				System.out.print(text);					
			} catch (RuntimeException ex) {
				throw new ShellIOException("Line write error", ex);
			}
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				System.out.println(text);					
			} catch (RuntimeException ex) {
				throw new ShellIOException("Line write error", ex);
			}			
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {			
			return Collections.unmodifiableSortedMap(map);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			this.multilineSymbol = symbol;
			
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			this.promptSymbol = symbol;
			
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			this.morelinesSymbol = symbol;
			
		}
		
	}
	
}
