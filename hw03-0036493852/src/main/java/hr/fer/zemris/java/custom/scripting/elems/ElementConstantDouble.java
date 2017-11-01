package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element subclass to store double numbers
 * 
 * @author Franko Car
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Double value
	 */
	private final double value;
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Constructor setting the value
	 * 
	 * @param value Double value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * A getter for value
	 * 
	 * @return value Double
	 */
	public double getValue() {
		return value;
	}	
}
