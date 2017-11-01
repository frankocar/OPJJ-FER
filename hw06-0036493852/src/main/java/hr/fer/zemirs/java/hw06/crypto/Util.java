package hr.fer.zemirs.java.hw06.crypto;

/**
 * A class with static methods for converting from hex strings to byte arrays and vice versa
 * 
 * @author Franko Car
 *
 */
public class Util {

	/**
	 * A method that converts from hex string to byte array. Hex string needs to be valid, otherwise
	 * an {@link IllegalArgumentException} is thrown.
	 * 
	 * @param keyText Hex string
	 * @return byte[] Array of bytes
	 * @throws IllegalArgumentException if the given string is not a valid hex value.
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Given key needs to be a valid hex value, given length is odd");
		}
				
		byte[] output = new byte[keyText.length() / 2];		
		for (int i = 0, l = keyText.length(); i < l; i += 2) {				
			int first = Character.digit(keyText.charAt(i), 16);
			int second = Character.digit(keyText.charAt(i + 1), 16);
			
			if (first == -1 || second == -1) {
				throw new IllegalArgumentException("Given string is not a valid hex value");
			}
			
			output[i / 2] = (byte) ((first * 16) + second);
		}
		
		return output;
	}
	
	/**
	 * A method that will convert an array of strings to hex string
	 * 
	 * @param in Byte array to convert
 	 * @return String representation of given byte array
	 */
	public static String bytetohex(byte[] in) {
		StringBuilder output = new StringBuilder();
		
		for (byte singleByte : in) {
			output.append(String.format("%02x", singleByte));
		}
		
		return output.toString();
	}
	
}
