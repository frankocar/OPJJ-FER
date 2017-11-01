package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node containing all text outside of '{$' and '&}' tags 
 * 
 * @author Franko Car
 *
 */
public class TextNode extends Node {

	/**
	 * A string storing the text
	 */
	private final String text;
	
	/**
	 * A constructor 
	 * 
	 * @param text Text to be stored
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	/**
	 * A getter for text
	 * 
	 * @return text String of text
	 */
	public String getText() {
		return text;
	}	
}
