package hr.fer.zemris.java.hw14.model;

/**
 * Bean that stores poll information
 * 
 * @author Franko Car
 *
 */
public class Poll {

	/**
	 * poll id
	 */
	private long id;
	/**
	 * title of a poll
	 */
	private String title;
	/**
	 * Poll message to the user
	 */
	private String message;
	
	/**
	 * A constructor
	 */
	public Poll() {
	}

	/**
	 * A getter for poll id
	 * 
	 * @return long poll ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * A setter for poll ID
	 * 
	 * @param id new poll ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * A getter for poll title
	 * 
	 * @return String poll title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * A setter for poll title
	 * 
	 * @param title new poll Title
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title can't be null");
		}
		this.title = title;
	}

	/**
	 * A getter for poll message
	 * 
	 * @return String poll message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * A setter for poll message
	 * 
	 * @param message new poll message
	 */
	public void setMessage(String message) {
		if (message == null) {
			throw new IllegalArgumentException("Message can't be null");
		}
		this.message = message;
	}
	
	
	
}
