package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;

/**
 * A statusbar component that always shows RGB components of currently selected colors
 * 
 * @author Franko Car
 *
 */
public class Statusbar extends JLabel {
	
	/** */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Format string to show the text
	 */
	private static final String TEXT_FORMAT = "Foreground color: (%s, %s, %s), background color: (%s, %s, %s)";
	/**
	 * Foreground color
	 */
	private Color fg;
	/**
	 * Background color
	 */
	private Color bg;

	/**
	 * A constructor
	 * 
	 * @param fgColorArea Provider of a foreground color
	 * @param bgColorArea Provider of a background color
	 */
	public Statusbar(JColorArea fgColorArea, JColorArea bgColorArea) {
		fg = fgColorArea.getCurrentColor();
		bg = bgColorArea.getCurrentColor();
		
		updateText();
		
		fgColorArea.addColorChangeListener((source, oldColor, newColor) -> {
			fg = newColor;
			updateText();
		});
		bgColorArea.addColorChangeListener((source, oldColor, newColor) -> {
			bg = newColor;
			updateText();
		});
		
	}

	/**
	 * Updates the text shown on a label
	 */
	private void updateText() {
		setText(String.format(TEXT_FORMAT, fg.getRed(), fg.getGreen(), fg.getBlue(), bg.getRed(), bg.getGreen(), bg.getBlue()));		
	}
	
}
