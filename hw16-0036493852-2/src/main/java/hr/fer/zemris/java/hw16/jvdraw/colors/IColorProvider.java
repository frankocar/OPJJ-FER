package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * Color provider interface for objects that notify ColorChangeListeners
 * about a change in color
 * 
 * @author Franko Car
 *
 */
public interface IColorProvider {

	/**
	 * Returns currently selected color
	 * 
	 * @return current color
	 */
	Color getCurrentColor();
	/**
	 * Add a new {@link ColorChangeListener}
	 * 
	 * @param l listener to add
	 */
	void addColorChangeListener(ColorChangeListener l);
	/**
	 * Remove an existing {@link ColorChangeListener}
	 * 
	 * @param l listener to remove
	 */
	void removeColorChangeListener(ColorChangeListener l);
	
}
