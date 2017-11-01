package hr.fer.zemris.java.custom.collections;

/**
 * An exception denoting that the stack is empty
 * 
 * @author Franko Car
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Serial version unique ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor with just a message
	 * 
	 * @param message A message describing the exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * A default constructor
	 */
	public EmptyStackException() {
		super();
	}	
}
