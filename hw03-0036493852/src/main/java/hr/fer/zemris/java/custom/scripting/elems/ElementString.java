package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An element subclass to store strings
 * 
 * @author Franko Car
 *
 */
public class ElementString extends Element {

	/**
	 * Stored string
	 */
	private final String string;
	
	@Override
	public String asText() {
		return string;
	}
	
	/**
	 * A constructor
	 * 
	 * @param string String
	 */
	public ElementString(String string) {
		this.string = string;
	}
	
	/**
	 * String getter
	 * 
	 * @return string String
	 */
	public String getString() {
		return string;
	}
}
