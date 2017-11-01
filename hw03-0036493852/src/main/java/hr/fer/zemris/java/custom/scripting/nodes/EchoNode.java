package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node subclass to store data from echo tags
 * 
 * @author Franko Car
 *
 */
public class EchoNode extends Node {

	/**
	 * Elements found in tags
	 */
	private final Element[] elements;

	/**
	 * A constructor taking in an array of elements
	 * 
	 * @param elements Element[] element array
	 */
	public EchoNode(Element[] elements) {
		super();
		
		if (elements == null) {
			throw new NullPointerException("Elements can't be null");
		}
		
		this.elements = elements;
	}
	
	/**
	 * A getter for stored element array
	 * 
	 * @return Element[] array
	 */
	public Element[] getElements() {
		return elements;
	}	
	
}
