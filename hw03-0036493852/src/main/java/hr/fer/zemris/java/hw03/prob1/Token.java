package hr.fer.zemris.java.hw03.prob1;

/**
 * A class describing a token used by lexer
 * to categorize input text.
 * 
 * @author Franko Car
 *
 */
public class Token {
	
	/**
	 * A type of token described by TokenType enum
	 */
	private TokenType type;
	/**
	 * Value of the token
	 */
	private Object value;

	/**
	 * A constructor for costructing a token using it's type and value
	 * 
	 * @param type TokenType type
	 * @param value Value of a token
	 */
	public Token(TokenType type, Object value) {
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
	public Object getValue() {
		return value;
	}
	
	/**
	 * A getter for type
	 * 
	 * @return type TokenType
	 */
	public TokenType getType() {
		return type;
	}
	
}
