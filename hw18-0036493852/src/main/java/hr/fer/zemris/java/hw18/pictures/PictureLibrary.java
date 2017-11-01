package hr.fer.zemris.java.hw18.pictures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * A singleton class that finds and stores all images described in a file
 * 
 * @author Franko Car
 *
 */
public class PictureLibrary {

	/**
	 * Counter to generate unique id's
	 */
	private static int counter = 1;
	/**
	 * Instance of the library used by all users
	 */
	private static PictureLibrary instance = new PictureLibrary();
	/**
	 * Map that maps pictures to their ID's
	 */
	private Map<Integer, Picture> pictures;
	/**
	 * Set of all tags found in images
	 */
	private Set<String> allTags;
	
	/**
	 * Default constructor
	 */
	private PictureLibrary() {}
	
	/**
	 * Returns an instance of this class
	 * 
	 * @return instance of PictureLibrary
	 */
	public static PictureLibrary getInstance() {		
		return instance;
	}
	
	/**
	 * Initializes a library
	 * 
	 * @param description Path to the file that describes images
	 * @param folder Folder that contains images
	 */
	public void init(Path description, Path folder) {
		if (pictures != null) {
			return;
		}
		
		pictures = new HashMap<>();
		allTags = new TreeSet<>();
		
		if (!Files.isDirectory(folder)) {
			throw new IllegalArgumentException("Invalid picture location");
		}
		
		List<String> input = null;
		try {
			input = Files.readAllLines(description);
		} catch (IOException ex) {
			System.out.println("Unable to read " + description.toString());
			System.exit(1);
		}
		
		if (input.size() % 3 != 0) {
			throw new IllegalArgumentException("Description file format is invalid");
		}
		
		for (int i = 0, n = input.size(); i < n; i+=3) {
			Picture pic = new Picture(counter);
			pic.setPicturePath(Paths.get(folder.toString() + "\\" + input.get(i)));
			pic.setTitle(input.get(i + 1));
			List<String> tags = Arrays.asList(input.get(i + 2).split(",")).stream().map(String::trim)
					.collect(Collectors.toList());
			pic.setTags(tags);
			allTags.addAll(tags);
			pictures.put(counter, pic);
			counter++;
		}		
	}
	
	/**
	 * A getter for a set of all tags
	 * 
	 * @return Tags set
	 */
	public Set<String> getAllTags() {
		if (allTags == null) {
			throw new IllegalAccessError("Library not initalized");
		}
		return allTags;
	}
	
	/**
	 * A getter for picture map
	 * 
	 * @return Picture map
	 */
	public Map<Integer, Picture> getPictures() {
		if (pictures == null) {
			throw new IllegalAccessError("Library not initalized");
		}
		return pictures;
	}
	
}
