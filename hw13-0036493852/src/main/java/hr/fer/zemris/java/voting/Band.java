package hr.fer.zemris.java.voting;

import java.io.Serializable;

/**
 * A bean that will store band information
 * 
 * @author Franko Car
 *
 */
public class Band implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Band ID
	 */
	private int id;
	/**
	 * Band name
	 */
	private String name;
	/**
	 * A link to a song that is representative of a band
	 */
	private String exampleLink;
	/**
	 * Number of votes band got
	 */
	int votes;
	
	/**
	 * Default constructor
	 */
	public Band() {
	}
	
	/**
	 * A constructor
	 * 
	 * @param id Band ID
	 * @param name Band name
	 * @param exampleLink A link to a song that is representative of a band
	 * @param votes Number of votes band got
	 */
	public Band(int id, String name, String exampleLink, int votes) {
		super();
		this.id = id;
		this.name = name;
		this.exampleLink = exampleLink;
		this.votes = votes;
	}

	/**
	 * A getter for ID
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * A setter for ID
	 * 
	 * @param id new ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * A getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * A setter for name
	 * 
	 * @param name new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A getter for example link URL
	 * 
	 * @return example link URL
	 */
	public String getExampleLink() {
		return exampleLink;
	}

	/**
	 * A setter for example link
	 * 
	 * @param exampleLink new link
	 */
	public void setExampleLink(String exampleLink) {
		this.exampleLink = exampleLink;
	}

	/**
	 * A getter for vote number
	 * 
	 * @return number of votes
	 */
	public int getVotes() {
		return votes;
	}
	
	/**
	 * A setter for number of votes
	 * 
	 * @param votes new number of votes
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	@Override
	public String toString() {
		return String.format("ID: %d; Name = %s; Votes = %d", id, name, votes);
	}
	
}
