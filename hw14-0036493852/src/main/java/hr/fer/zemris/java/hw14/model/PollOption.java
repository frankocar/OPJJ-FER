package hr.fer.zemris.java.hw14.model;

/**
 * Bean to store all options in a poll
 * 
 * @author Franko Car
 *
 */
public class PollOption {

	/**
	 * option ID
	 */
	private long id;
	/**
	 * Title of an option
	 */
	private String optionTitle;
	/**
	 * A link describing the option
	 */
	private String optionLink;
	/**
	 * ID of a poll containing the option
	 */
	private long pollID;
	/**
	 * Number of votes
	 */
	private long votesCount;
	
	/**
	 * A constructor
	 */
	public PollOption(){
	}

	/**
	 * A getter for option ID
	 * 
	 * @return long option ID
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for option ID
	 * 
	 * @param id new option ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * A getter for option title
	 * 
	 * @return String option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * A setter for option title
	 * 
	 * @param optionTitle new option title
	 */
	public void setOptionTitle(String optionTitle) {
		if (optionTitle == null) {
			throw new IllegalArgumentException("Option title can't be null");
		}
		this.optionTitle = optionTitle;
	}

	/**
	 * A getter for option link
	 * 
	 * @return String option link
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * A setter for option link
	 * 
	 * @param optionLink new option link
	 */
	public void setOptionLink(String optionLink) {
		if (optionLink == null) {
			throw new IllegalArgumentException("Option link can't be null");
		}
		this.optionLink = optionLink;
	}

	/**
	 * A getter for poll ID
	 * 
	 * @return long poll ID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for poll ID
	 * 
	 * @param pollID new poll ID
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * A getter for vote count
	 * 
	 * @return long vote count
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for vote count
	 * 
	 * @param votesCount new vote count
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
	
	
	
}
