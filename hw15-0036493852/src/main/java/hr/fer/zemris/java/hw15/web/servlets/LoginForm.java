package hr.fer.zemris.java.hw15.web.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * A class that will check the form filled by the user for errors
 * 
 * @author Franko Car
 *
 */
public class LoginForm {

	/**
	 * Entered nickname
	 */
	private String nick;
	/**
	 * Entered password
	 */
	private String password;
	/**
	 * User, if found in the database
	 */
	private BlogUser user;
	
	/**
	 * Map of errors found
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * A constructor that will read all parameters form {@link HttpServletRequest}
	 * 
	 * @param req HttpServletRequest with data
	 */
	public LoginForm(HttpServletRequest req) {
		this.nick = req.getParameter("user").trim();
		this.password = req.getParameter("pass");
		validate();
	}
	
	/**
	 * Validates the stored data and puts all found errors in a map
	 */
	public void validate() {
		errors.clear();
		
		user = DAOProvider.getDAO().getUser(nick);
		if (user == null) {
			errors.put("nick", "User with given username does not exist, please register first");
		}
		
		if (nick == null || nick.isEmpty()) {
			errors.put("nick", "You must enter a username");
		}
		
		if (user != null) {
			if (!Utilities.comparePasswords(password, user.getPasswordHash())) {
				errors.put("password", "There is no record with mathching username/passwor combination");
			}			
		}		
		
		if (password == null || password.isEmpty()) {
			errors.put("password", "You must enter a password");
		} 
	}

	/**
	 * A getter for entered username
	 * 
	 * @return String entered username
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * A setter for username
	 * 
	 * @param nick new username
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * A getter for entered password
	 * 
	 * @return String entered password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for stored password
	 * 
 	 * @param password new stored password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for map of errors
	 * 
	 * @return map of errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/**
	 * Getter for single error
	 * 
	 * @param error requested error
	 * @return String requested error
	 */
	public String getError(String error) {
		return errors.get(error);
	}
	
	/**
	 * Checks if there are any errors found
	 * 
	 * @return true if errors are found, false otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Checks if there is a certain error
	 * 
	 * @param error error to check
	 * @return true if found, false otherwise
	 */
	public boolean hasError(String error) {
		return errors.containsKey(error);
	}

	/**
	 * Gets user with a given username if it already exists
	 * 
	 * @return BlogUser user
	 */
	public BlogUser getUser() {
		return user;
	}	
}
