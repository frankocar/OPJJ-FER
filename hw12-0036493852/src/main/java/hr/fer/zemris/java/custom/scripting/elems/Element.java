package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A top level class to store elements.
 * It itself chouldn't be used, use appropriate subclasses.
 * 
 * @author Franko Car
 *
 */
public class Element {

	/**
	 * Returns element as a String
	 * 
	 * @return text String
	 */
	public String asText() {
		return "";
	}
	
	/**
	 * Returns the value of an element if the element is a constant value
	 * 
	 * @return Element value
	 */
	public Object getElementValue() {
		if (this instanceof ElementConstantDouble) {
			return ((ElementConstantDouble)this).getValue();
		} else if (this instanceof ElementConstantInteger) {
			return ((ElementConstantInteger)this).getValue();
		} else if (this instanceof ElementString) {
			return ((ElementString)this).asText();
		} else {
			throw new RuntimeException("Element isn't a value");
		}
	}
	
}
