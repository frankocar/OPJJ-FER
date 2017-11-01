package hr.fer.zemris.java.gui.charts;

/**
 * A class to store individual values to show on a chart
 * 
 * @author Franko Car
 *
 */
public class XYValue {


	/** X axis value */
	private int x;
	/** Y axis value */
	private int y;
	
	/**
	 * A constructor
	 * 
	 * @param x value
	 * @param y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * X axis value getter
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Y axis value getter
	 * 
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
}
