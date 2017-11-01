package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An element subclass to store functions
 * 
 * @author Franko Car
 *
 */
public class ElementFunction extends Element {

	/**
	 * Name of a function
	 */
	private final String name;
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * A constructor
	 * 
	 * @param name String function name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * A getter for a name
	 * 
	 * @return name String
	 */
	public String getName() {
		return name;
	}
}
