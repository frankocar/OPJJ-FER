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
 * Class that models a simple filed circle
 * 
 * @author Franko Car
 *
 */
public class FilledCircle extends GeometricalObject {

	/**
	 * Outline color
	 */
	private Color fgColor;
	/**
	 * Fill color
	 */
	private Color bgColor;
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
	 * Input field for foreground color
	 */
	private JColorArea fgColorChooser;
	/**
	 * Input field for background color
	 */
	private JColorArea bgColorChooser;
	
	/**
	 * A constructor
	 * 
	 * @param startX starting X coordinate
	 * @param endX ending X coordinate
	 * @param startY starting Y coordinate
	 * @param endY ending Y coordinate
	 * @param fgColor foreground color
	 * @param bgColor background color
	 */
	public FilledCircle(int startX, int endX, int startY, int endY, Color fgColor, Color bgColor) {
		super(startX, endX, startY, endY, "FilledCircle: " + counter++);
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}
	
	/**
	 * A constructor
	 * 
	 * @param fgColor foreground color
	 * @param bgColor background color
	 */
	public FilledCircle(Color fgColor, Color bgColor) {
		super("FilledCircle " + counter++);
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}

	@Override
	public void draw(Graphics2D g) {
		radius = (int) Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));
		g.setColor(bgColor);
		g.fillOval(startX - radius, startY - radius, 2 * radius, 2 * radius);
		g.setColor(fgColor);
		g.drawOval(startX - radius, startY - radius, 2 * radius, 2 * radius);
	}

	@Override
	public JPanel getOptions() {
		centerXInput = new JTextField(String.valueOf(startX));
		centerYInput = new JTextField(String.valueOf(startY));
		radiusInput = new JTextField(String.valueOf(radius));
		fgColorChooser = new JColorArea(fgColor);
		bgColorChooser = new JColorArea(bgColor);
		
		JPanel pane = new JPanel(new GridLayout(5, 2));
		pane.add(new JLabel("Centerpoint X cord."));
		pane.add(centerXInput);
		pane.add(new JLabel("Centerpoint Y cord."));
		pane.add(centerYInput);
		pane.add(new JLabel("Radius"));
		pane.add(radiusInput);
		pane.add(new JLabel("Line color"));
		pane.add(fgColorChooser);
		pane.add(new JLabel("Fill color"));
		pane.add(bgColorChooser);
		
		
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
			fgColor = fgColorChooser.getCurrentColor();
			bgColor = bgColorChooser.getCurrentColor();
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
		return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", startX, startY, radius, fgColor.getRed(),
				fgColor.getGreen(), fgColor.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
	}
	
}
