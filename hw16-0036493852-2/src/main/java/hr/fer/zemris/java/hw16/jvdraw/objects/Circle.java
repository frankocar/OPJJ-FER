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
 * Class that models a simple circle
 * 
 * @author Franko Car
 *
 */
public class Circle extends GeometricalObject {
	
	/**
	 * Outline color
	 */
	private Color color;
	/**
	 * Counts the number of lines
	 */
	private static int counter = 1;
	/**
	 * Circle radius
	 */
	int radius;
	
	/**
	 * Input field for center x coordinate
	 */
	private JTextField centerXInput;
	/**
	 * Input field for center y coordinate
	 */
	private JTextField centerYInput;
	/**
	 * Input field for radius
	 */
	private JTextField radiusInput;
	/**
	 * Input field for color
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
	public Circle(int startX, int endX, int startY, int endY, Color color) {
		super(startX, endX, startY, endY, "Circle: " + counter++);
		this.color = color;
	}
	
	/**
	 * A constructor
	 * 
	 * @param color color
	 */
	public Circle(Color color) {
		super("Circle " + counter++);
		this.color = color;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		radius = (int) Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));
		g.drawOval(startX - radius, startY - radius, 2 * radius, 2 * radius);
	}

	@Override
	public JPanel getOptions() {
		centerXInput = new JTextField(String.valueOf(startX));
		centerYInput = new JTextField(String.valueOf(startY));
		radiusInput = new JTextField(String.valueOf(radius));
		colorChooser = new JColorArea(color);
		
		JPanel pane = new JPanel(new GridLayout(4, 2));
		pane.add(new JLabel("Centerpoint X cord."));
		pane.add(centerXInput);
		pane.add(new JLabel("Centerpoint Y cord."));
		pane.add(centerYInput);
		pane.add(new JLabel("Radius"));
		pane.add(radiusInput);
		pane.add(new JLabel("Color"));
		pane.add(colorChooser);
		
		
		return pane;
	}

	@Override
	public void applyOptions() {
		try {
			int centerXIn = Integer.parseInt(centerXInput.getText());
			int centerYIn = Integer.parseInt(centerYInput.getText());
			int radiusIn = Integer.parseInt(radiusInput.getText());
			System.out.println(radiusIn);
			startX = centerXIn;
			startY = centerYIn;
			endX = startX + radiusIn;
			endY = startY;
			color = colorChooser.getCurrentColor();			
		} catch (NumberFormatException | NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Invalid options entered", "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}

	@Override
	public int getMinX() {
		return startX - radius;
	}

	@Override
	public int getMinY() {
		return startY - radius;
	}

	@Override
	public int getMaxX() {
		return startX + radius;
	}

	@Override
	public int getMaxY() {
		return startY + radius;
	}

	@Override
	public String export() {
		return String.format("CIRCLE %d %d %d %d %d %d", startX, startY, radius, color.getRed(), color.getGreen(), color.getBlue());
	}

}
