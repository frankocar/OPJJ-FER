package hr.fer.zemris.bf.lexer;

/**
 * An exception to be thrown when lexer encounters unexpected data
 * 
 * @author Franko Car
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * A constructor that takes in a message
	 * 
	 * @param message A message
	 */
	public LexerException(String message) {
		super(message);		
	}
	
	/**
	 * A constructor that takes in a message and a throwable to forward
	 * 
	 * @param message A message
	 * @param throwable A throwable
	 */
	public LexerException(String message, Throwable throwable) {
		super(message, throwable);		
	}
	
	/**
	 * A constructor that takes in a throwable to forward
	 * 
	 * @param throwable A throwable
	 */
	public LexerException(Throwable throwable) {
		super(throwable);		
	}
	
}