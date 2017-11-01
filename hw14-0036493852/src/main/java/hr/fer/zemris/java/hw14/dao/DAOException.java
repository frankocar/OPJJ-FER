package hr.fer.zemris.java.hw14.dao;

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
	 * Empty constructor
	 */
	public DAOException() {
	}

	/**
	 * A constructor
	 * 
	 * @param message  the detail message.
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param enableSuppression whether or not suppression is enabled or disabled
	 * @param writableStackTrace whether or not the stack trace should be writable
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

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

	/**
	 * A constructor
	 * 
	 * @param cause the cause. (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}