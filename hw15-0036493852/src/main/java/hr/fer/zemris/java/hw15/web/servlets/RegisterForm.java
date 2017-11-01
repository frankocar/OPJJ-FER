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
public class RegisterForm {
	/**
	 * Entered username
	 */
	private String nick;
	/**
	 * Entered password
	 */
	private String password;
	/**
	 * Entered first name
	 */
	private String firstName;
	/**
	 * Entered last name
	 */
	private String lastName;
	/**
	 * Entered email
	 */
	private String email;
	
	/**
	 * Map of errors
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * A constructor that will read all parameters form {@link HttpServletRequest}
	 * 
	 * @param req HttpServletRequest with data
	 */
	public RegisterForm(HttpServletRequest req) {
		this.nick = req.getParameter("user").trim();
		this.password = req.getParameter("pass");
		this.firstName = req.getParameter("firstName").trim();
		this.lastName = req.getParameter("lastName").trim();
		this.email = req.getParameter("email").trim();
		validate();
	}
	
	/**
	 * Validates the stored data and puts all found errors in a map
	 */
	public void validate() {
		errors.clear();
		
		BlogUser user = DAOProvider.getDAO().getUser(nick);
		if (user != null) {
			errors.put("nick", "User with given username already exists.");
		}
		
		if (nick == null || nick.isEmpty()) {
			errors.put("nick", "You must enter a username");
		}
		
		if (firstName == null || firstName.isEmpty()) {
			errors.put("firstName", "You must enter your name");
		}
		
		if (lastName == null || lastName.isEmpty()) {
			errors.put("lastName", "You must enter your last name");
		}
		
		if (email == null || email.isEmpty()) {
			errors.put("email", "You must enter your email");
		}	
		
		if (password == null || password.isEmpty()) {
			errors.put("password", "You must enter your password");
		}	
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
	 * Getter for entered nickname
	 * 
	 * @return entered nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nickname 
	 * 
	 * @param nick new nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for entered password
	 * 
	 * @return entered password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter for password
	 * 
	 * @param password new pasword
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Getter for entered first name
	 * 
	 * @return entered first naem
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for first name 
	 * 
	 * @param firstName new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for entered last name
	 * 
	 * @return entered last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for last name
	 * 
	 * @param lastName new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for entered email
	 * 
	 * @return entered email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
