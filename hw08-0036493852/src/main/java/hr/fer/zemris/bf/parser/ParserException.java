package hr.fer.zemris.bf.parser;

/**
 * An exception to be thrown when parser encounters unexpected data
 * 
 * @author Franko Car
 *
 */
public class ParserException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor
	 */
	public ParserException() {
		super();
	}
	
	/**
	 * A constructor that takes in a message
	 * 
	 * @param message A message
	 */
	public ParserException(String message) {
		super(message);		
	}
	
	/**
	 * A constructor that takes in a message and a throwable to forward
	 * 
	 * @param message A message
	 * @param throwable A throwable
	 */
	public ParserException(String message, Throwable throwable) {
		super(message, throwable);		
	}
	
	/**
	 * A constructor that takes in a throwable to forward
	 * 
	 * @param throwable A throwable
	 */
	public ParserException(Throwable throwable) {
		super(throwable);		
	}
	
}
