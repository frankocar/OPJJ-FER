package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An element subclass to store variables
 * 
 * @author Franko Car
 *
 */
public class ElementVariable extends Element {

	/**
	 * A name of a variable
	 */
	private final String name;
	
	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * A constructor
	 * 
	 * @param name String variable name
	 */
	public ElementVariable(String name) {
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
