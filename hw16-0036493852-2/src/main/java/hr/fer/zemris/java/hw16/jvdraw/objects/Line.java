package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.colors.JColorArea;

/**
 * Class that models a simple line shape
 * 
 * @author Franko Car
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Line color
	 */
	private Color color;
	/**
	 * Counts the number of lines
	 */
	private static int counter = 1;
	
	/**
	 * Input field for starting X coordinate
	 */
	private JTextField startXInput;
	/**
	 * Input field for starting Y coordinate
	 */
	private JTextField startYInput;
	/**
	 * Input field for ending X coordinate
	 */
	private JTextField endXInput;
	/**
	 * Input field for ending Y coordinate
	 */
	private JTextField endYInput;
	/**
	 * Color chooser for change of color
	 */
	private JColorArea colorChooser;
	
	/**
	 * A constructor
	 * 
	 * @param startX starting X coordinate
	 * @param endX ending X coordinate
	 * @param startY starting Y coordinate
	 * @param endY ending Y coordinate
	 * @param color color
	 */
	public Line(int startX, int endX, int startY, int endY, Color color) {
		super(startX, endX, startY, endY, "Line: " + counter++);
		this.color = color;
	}
	
	/**
	 * A constructor
	 * 
	 * @param color color
	 */
	public Line(Color color) {
		super("Line " + counter++);
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.drawLine(startX, startY, endX, endY);
	}

	@Override
	public JPanel getOptions() {
		startXInput = new JTextField(String.valueOf(startX));
		startYInput = new JTextField(String.valueOf(startY));
		endXInput = new JTextField(String.valueOf(endX));
		endYInput = new JTextField(String.valueOf(endY));
		colorChooser = new JColorArea(color);
		
		JPanel pane = new JPanel(new GridLayout(5, 2));
		pane.add(new JLabel("Start X coord."));
		pane.add(startXInput);
		pane.add(new JLabel("Start Y coord."));
		pane.add(startYInput);
		pane.add(new JLabel("End X coord."));
		pane.add(endXInput);
		pane.add(new JLabel("End Y coord."));
		pane.add(endYInput);
		pane.add(new JLabel("Color"));
		pane.add(colorChooser);
		
		
		return pane;
	}

	@Override
	public void applyOptions() {
		try {
			startX = Integer.parseInt(startXInput.getText());
			startY = Integer.parseInt(startYInput.getText());
			endX = Integer.parseInt(endXInput.getText());
			endY = Integer.parseInt(endYInput.getText());
			color = colorChooser.getCurrentColor();			
		} catch (NumberFormatException | NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Invalid options entered", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	@Override
	public int getMinX() {
		return Math.min(startX, endX);
	}

	@Override
	public int getMinY() {
		return Math.min(startY, endY);
	}

	@Override
	public int getMaxX() {
		return Math.max(startX, endX);
	}

	@Override
	public int getMaxY() {
		return Math.max(startY, endY);
	}

	@Override
	public String export() {
		return String.format("LINE %d %d %d %d %d %d %d", startX, startY, endX, endY, color.getRed(), color.getGreen(), color.getBlue());
	}

}
