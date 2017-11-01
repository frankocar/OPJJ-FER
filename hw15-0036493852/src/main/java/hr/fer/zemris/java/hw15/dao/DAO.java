package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

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
	 * Gets a blog entry with a given <code>id</code>. If an entry doesn't exist
	 * it returns a  <code>null</code>.
	 * 
	 * @param id entry key
	 * @return entry or <code>null</code> if one doesn't exist
	 * @throws DAOException data access error
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Gets a blog user with a given <code>id</code>. If a user doesn't exist
	 * it returns a  <code>null</code>.
	 * 
	 * @param id user key
	 * @return entry or <code>null</code> if one doesn't exist
	 * @throws DAOException data access error
	 */
	public BlogUser getUser(Long id) throws DAOException;
	
	/**
	 * Gets a list of registerd blog users.
	 * 
	 * @return List<BlogUser> list of users
	 * @throws DAOException data access error
	 */
	public List<BlogUser> getUsers() throws DAOException;
	
	/**
	 * Gets a blog user with a given <code>nick</code>. If a user doesn't exist
	 * it returns a  <code>null</code>.
	 * 
	 * @param nick user nick
	 * @return entry or <code>null</code> if one doesn't exist
	 * @throws DAOException data access error
	 */
	public BlogUser getUser(String nick) throws DAOException;
	
	/**
	 * Adds a new user to the database
	 * 
	 * @param newUser user to add
	 * @throws DAOException data access error
	 */
	public void addNewUser(BlogUser newUser) throws DAOException;
	
	/**
	 * Gets a list of entries made by a given user
	 * 
	 * @param user User who made the entries
	 * @return List<BlogEntry> list of entries
	 * @throws DAOException data access error
	 */
	public List<BlogEntry> getUserEntries(BlogUser user) throws DAOException;
	
	/**
	 * Adds a new entry to the database
	 * 
	 * @param entry entry to add
	 * @throws DAOException data access error
	 */
	public void addEntry(BlogEntry entry) throws DAOException;
	
	/**
	 * Adds a new comment to the database
	 * 
	 * @param comment user to add
	 * @throws DAOException data access error
	 */
	public void addComment(BlogComment comment) throws DAOException;
	
}