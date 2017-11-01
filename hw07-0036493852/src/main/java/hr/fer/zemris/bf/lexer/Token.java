package hr.fer.zemris.bf.lexer;

/**
 * A class describing a Token used by {@link Lexer}
 * to categorize input text.
 * 
 * @author Franko Car
 *
 */
public class Token {
	
	/**
	 * A type of token described by {@link TokenType} enum
	 */
	private TokenType type;
	/**
	 * Value of the token
	 */
	private Object value;

	/**
	 * A constructor for constructing a token using it's type and value
	 * 
	 * @param tokenType TokenType type
	 * @param tokenValue Value of a token
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		if (tokenType == null) {
			throw new LexerException("Token type can't be null");
		}
		
		this.type = tokenType;
		this.value = tokenValue;
	}
	
	/**
	 * A getter for type
	 * 
	 * @return type TokenType
	 */
	public TokenType getTokenType() {
		return type;
	}
	
	/**
	 * A getter for value
	 * 
	 * @return value Object
	 */
	public Object getTokenValue() {
		return value;
	}
	
	@Override
	public String toString() {
		if (value == null) {
			return String.format("Type: %s, Value: null", type);
		}
		return String.format("Type: %s, Value: %s, Value is instance of: %s", type, value.toString(), value.getClass().getCanonicalName());
	}
	
}
