package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A class describing a SmartScriptToken used by SmartScriptLexer
 * to categorize input text.
 * 
 * @author Franko Car
 *
 */
public class SmartScriptToken {
	
	/**
	 * A type of token described by SmartScriptTokenType enum
	 */
	private SmartScriptTokenType type;
	/**
	 * Value of the token
	 */
	private Element value;

	/**
	 * A constructor for constructing a token using it's type and value
	 * 
	 * @param type SmartScriptTokenType type
	 * @param value Value of a token
	 */
	public SmartScriptToken(SmartScriptTokenType type, Element value) {
		if (type == null) {
			throw new IllegalArgumentException("Token type can't be null");
		}
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * A getter for value
	 * 
	 * @return value Object
	 */
	public Element getValue() {
		return value;
	}
	
	/**
	 * A getter for type
	 * 
	 * @return type TokenType
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return String.format("Type: %s, value: %s", type, value.asText());
	}
	
}
