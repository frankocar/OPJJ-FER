package hr.fer.zemris.java.hw15.web.servlets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class with static methods for converting byte arrays to strings and password hashing operations.
 * 
 * @author Franko Car
 *
 */
public class Utilities {
	
	/**
	 * A method that will convert an array of strings to hex string
	 * 
	 * @param in Byte array to convert
 	 * @return String representation of given byte array
	 */
	private static String bytetohex(byte[] in) {
		StringBuilder output = new StringBuilder();
		
		for (byte singleByte : in) {
			output.append(String.format("%02x", singleByte));
		}
		
		return output.toString();
	}
	
	/**
	 * Calculates the SHA1 hash value of a given password string
	 * 
	 * @param password String to hash
	 * @return String hashed value
	 */
	public static String calcHash(String password) {
		if (password == null) {
			password = "";
		}
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(password.getBytes());
			return bytetohex(md.digest());
		} catch (NoSuchAlgorithmException ex) {
			System.err.println("Unable to hash the password");
		}
		return null;
	}
	
	/**
	 * Compares the given plain text password to a hashed stored password
	 * 
	 * @param password Plain text string to check
	 * @param hash Hashed password to compare against
	 * @return true if passwords match, false otherwise
	 */
	public static boolean comparePasswords(String password, String hash) {
		String hashedInput = calcHash(password);
		
		if (hashedInput == null) {
			System.err.println("Unable to hash the password");
			return false;
		}
		
		return hash.equals(hashedInput);
	}
	
	/**
	 * Checks if a given string can be read as a numeric value in long format
	 * 
	 * @param str String to check
	 * @return true if value is numeric, false otherwise
	 */
	public static boolean isNumeric(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
}
