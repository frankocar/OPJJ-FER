package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * Color change listener, its implementations listen to {@link IColorProvider}
 * color changes and act accordingly.
 * 
 * @author Franko Car
 *
 */
public interface ColorChangeListener {

	/**
	 * Method called each time a new color is selected
	 * 
	 * @param source source of a change
	 * @param oldColor old color
	 * @param newColor new color
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}
