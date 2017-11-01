package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface used by DAO data interface subsystem. Allows the data
 * to be read, poll options can be associated with their respective polls
 * and votes can be added
 * 
 * @author Franko Car
 *
 */
public interface DAO {

	/**
	 * Returns the list of {@link Poll} objects with available poll data.
	 * Reads the data from a database connection.
	 * 
	 * @return List<Poll> list of {@link Poll} objects
	 */
	List<Poll> getPolls();
	
	/**
	 * Returns a {@link Poll} object with a given ID.
	 * Reads the data from a database connection.
	 *  
	 * @param pollID ID of a poll to get
	 * @return Poll with a requested ID
	 */
	Poll getPoll(long pollID);
	
	/**
	 * Returns the list of {@link PollOption} objects with available data.
	 * Reads the data from a database connection.
	 * 
	 * @param pollID ID of a poll to search
	 * @return List<PollOption> list of {@link PollOption} objects
	 */
	List<PollOption> getPollOptions(long pollID);
	
	/**
	 * Adds a single vote to an option with a given ID.
	 * 
	 * @param optionId
	 */
	void addVote(long optionId);
	
	/**
	 * Returns the number of votes of an option with a given ID
	 * 
	 * @param optionId
	 * @return long number of votes
	 */
	long getVotes(long optionId);
	
	/**
	 * Returns a {@link Poll} that contains an option with a given ID
	 * 
	 * @param optionId option ID
	 * @return long ID of a poll
	 */
	long getPollFromOptionID(long optionId);
	
	
	
}