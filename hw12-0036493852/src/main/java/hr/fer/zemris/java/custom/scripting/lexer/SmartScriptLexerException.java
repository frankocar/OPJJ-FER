package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An exception to be thrown when lexer encounters unexpected data
 * 
 * @author Franko Car
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * A constructor that takes in a message
	 * 
	 * @param message A message
	 */
	public SmartScriptLexerException(String message) {
		super(message);		
	}
	
	/**
	 * A constructor that takes in a message and a throwable to forward
	 * 
	 * @param message A message
	 * @param throwable A throwable
	 */
	public SmartScriptLexerException(String message, Throwable throwable) {
		super(message, throwable);		
	}
	
	/**
	 * A constructor that takes in a throwable to forward
	 * 
	 * @param throwable A throwable
	 */
	public SmartScriptLexerException(Throwable throwable) {
		super(throwable);		
	}
	
}
