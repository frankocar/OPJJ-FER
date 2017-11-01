package hr.fer.zemris.java.hw11.jnotepadapp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * A class with static methods to load Icons form program resources
 * 
 * @author Franko Car
 *
 */
public class IconLoader {

	/**
	 * Loads an {@link Icon} from a local path
	 * 
	 * @param path Path to the icon
	 * @param caller Caller to whom the path is relative
	 * @return Icon at a given path
	 */
	public static Icon loadIcon(String path, Object caller) {
		InputStream is = caller.getClass().getResourceAsStream(path); 
		if (is == null) {
			throw new IllegalArgumentException("Can't find requested icon: " + path);
		}
		byte[] bytes = readAllBytes(is);
		
		try {
			is.close();
		} catch (IOException e) {
			System.err.println("Unable to close stream");
			System.exit(1);
		}
		return new ImageIcon(bytes);
	}

	/**
	 * A method to read an {@link InputStream} to a byte array
	 * 
	 * @param is {@link InputStream} to use
	 * @return byte array
	 */
	private static byte[] readAllBytes(InputStream is) {
		if (is == null) {
			System.err.println("Unable to open stream");
			System.exit(1);
		}
		
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		
		try {
			byte[] buffer = new byte[1024];
			int read = 0;
			
			while ((read = is.read(buffer)) != -1) {
				data.write(buffer, 0, read);
			}
		} catch (IOException ex) {
			System.err.println("Unable to read from stream");
			System.exit(1);
		}
		
		return data.toByteArray();
	}
	
}
