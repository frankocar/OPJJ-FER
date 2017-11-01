package hr.fer.zemirs.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemirs.java.hw06.shell.Environment;
import hr.fer.zemirs.java.hw06.shell.ShellStatus;

/**
 * Command 'ls' will print out the contents of a given directory along with some extra details 
 * First column shows if file is: a directory, readable, writable and executable 
 * Second column shows the size in bytes 
 * Third column show the creation time 
 * Fourth column shows the filename 
 * A single argument is required, a path to the directory to list 
 * Usage: 'ls [directory]'
 * 
 * @author Franko Car
 *
 */
public class LsCommand implements ShellCommand {

	/** Command name */
	private static final String NAME = "ls";
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = ArgumentParser.singlePathArgument(arguments, getCommandName());
		
		if (!Files.isDirectory(path)) {
			throw new IllegalArgumentException("Argument must be a directory");
		}
		
		List<Path> list = null;
		try {
			list = Files.list(path).collect(Collectors.toList());
		} catch (IOException ex) {
			throw new RuntimeException("Reading files unsuccessful: " + ex.getMessage(), ex);
		}
		
		try {
			printFiles(env, list);
		} catch (IOException ex) {
			throw new RuntimeException("File access error: " + ex.getMessage(), ex);
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * A method that will print out a list of files along their details
	 * 
	 * @param env Environment to print in
	 * @param list List of files
	 * @throws IOException If files can't be accessed
	 */
	private void printFiles(Environment env, List<Path> list) throws IOException {
		for (Path file : list) {
			StringBuilder sb = new StringBuilder();
			sb.append(Files.isDirectory(file) ? "d" : "-");
			sb.append(Files.isReadable(file) ? "r" : "-");
			sb.append(Files.isWritable(file) ? "w" : "-");
			sb.append(Files.isExecutable(file) ? "x" : "-");			
			sb.append(" ");
			
			sb.append(String.format("%10d", Files.size(file)));			
			sb.append(" ");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Path path = file;
			BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
					LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			sb.append(formattedDateTime);			
			sb.append(" ");
			
			sb.append(file.getFileName());
			
			env.writeln(sb.toString());
		}	
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command 'ls' will print out the contents of a given directory along with some extra details");
		desc.add("First column shows if file is: a directory, readable, writable and executable");
		desc.add("Second column shows the size in bytes");
		desc.add("Third column show the creation time");
		desc.add("Fourth column shows the filename");
		desc.add("A single argument is required, a path to the directory to list");
		desc.add("Usage: 'ls [directory]'");
		return Collections.unmodifiableList(desc);
	}

}
