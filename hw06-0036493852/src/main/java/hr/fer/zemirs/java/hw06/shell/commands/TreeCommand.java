package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'tree' will recursively print out the contents of a given directory 
 * A single argument is required, a path to the directory to traverse 
 * Usage: 'tree [directory]'
 * 
 * @author Franko Car
 *
 */
public class TreeCommand implements ShellCommand {
	
	/** Command name */
	private static final String NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {		
		Path path = ArgumentParser.singlePathArgument(arguments, getCommandName());
		
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Argument must be a directory");
		}
		
		try {
			Files.walkFileTree(path, new Walker(env));
		} catch (IOException ex) {
			throw new IllegalArgumentException("Argument must be a valid directory", ex);
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
		desc.add("Command 'tree' will recursively print out the contents of a given directory");
		desc.add("A single argument is required, a path to the directory to traverse");
		desc.add("Usage: 'tree [directory]'");
		return Collections.unmodifiableList(desc);
	}

	/**
	 * An implementation of FileVisitor interface to be used by the tree command
	 * 
	 * @author Franko Car
	 *
	 */
	private static class Walker implements FileVisitor<Path> {

		/** Current filesystem depth from root */
		private int level;
		/** Environment to use */
		private Environment env;
		
		/**
		 * Constructor taking in the environment to use
		 * 
		 * @param env environment
		 */
		public Walker(Environment env) {
			this.env = env;
		}
		
		/**
		 * A method to print the current file at an appropriate level of indentation
		 * 
		 * @param file current file
		 */
		private void print(Path file) {
			if (level == 0) {
				env.writeln(file.toString());
			} else {
				env.writeln(String.format("%" + (2 * level) + "s%s", "", file.toString()));
			}
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}
	
}
