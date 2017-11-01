package hr.fer.zemris.java.custom.scripting.parser;

/**
 * An exception to be thrown when parser encounters unexpected data
 * 
 * @author Franko Car
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * A constructor that takes in a throwable to forward
	 * 
	 * @param throwable A throwable
	 */
	public SmartScriptParserException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * A constructor that takes in a message
	 * 
	 * @param message A message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * A constructor that takes in a message and a throwable to forward
	 * 
	 * @param message A message
	 * @param throwable A throwable
	 */
	public SmartScriptParserException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
