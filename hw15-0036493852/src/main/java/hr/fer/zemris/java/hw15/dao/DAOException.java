package hr.fer.zemris.java.hw15.dao;

/**
 * Exception thrown when a DAO subsytem error is detected
 * 
 * @author Franko Car
 *
 */
public class DAOException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor
	 * 
	 * @param message  the detail message.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * A constructor
	 * 
	 * @param message  the detail message.
	 */
	public DAOException(String message) {
		super(message);
	}
}