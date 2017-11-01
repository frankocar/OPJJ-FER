package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * A class to store bar chart model data
 * 
 * @author Franko Car
 *
 */
public class BarChart {

	/** List of XYValues */
	private List<XYValue> values;
	/** X axis description */
	private String xDescription;
	/** Y axis description */
	private String yDescription;
	/** Lowest value shown on y axis */
	private int minY;
	/** Highest value shown on y axis */
	private int maxY;
	/** Difference between two y axis values */
	private int step;
	
	/**
	 * A constructor
	 * 
	 * @param values List of XYValues
	 * @param xDescription X axis description
	 * @param yDescription Y axis description
	 * @param minY Lowest value shown on y axis
	 * @param maxY Highest value shown on y axis
	 * @param step Difference between two y axis values
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int minY, int maxY, int step) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = (maxY += ((maxY - minY) % step));
		this.step = step;		
	}

	/**
	 * A getter for list of values
	 * 
	 * @return list of values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * A getter for x axis description
	 * 
	 * @return x axis description
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * A getter for y axis description
	 * 
	 * @return y axis description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * A getter for y axis lowest value
	 * 
	 * @return minY
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * A getter for y axis highest value
	 * 
	 * @return maxY
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * A getter for y axis step
	 * 
	 * @return step
	 */
	public int getStep() {
		return step;
	}	
}
