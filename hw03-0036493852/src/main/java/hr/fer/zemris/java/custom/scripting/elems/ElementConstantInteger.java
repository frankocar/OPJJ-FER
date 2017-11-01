package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element subclass to store integer numbers
 * 
 * @author Franko Car
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Integer value
	 */
	private final int value;
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Constructor setting the value
	 * 
	 * @param value int value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * A getter for value
	 * 
	 * @return value int
	 */
	public int getValue() {
		return value;
	}
}
