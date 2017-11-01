package hr.fer.zemirs.java.hw06.shell;

/**
 * An exception thrown when shell encounters an IO exception
 * 
 * @author Franko Car
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A default constructor
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * A constructor that takes in a message
	 * 
	 * @param message A message
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * A constructor that takes in a throwable to forward
	 * 
	 * @param throwable A throwable
	 */
	public ShellIOException(Throwable throwable) {
		super(throwable);
	}
	
	/**
	 * A constructor that takes in a message and a throwable to forward
	 * 
	 * @param message A message
	 * @param throwable A throwable
	 */
	public ShellIOException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
