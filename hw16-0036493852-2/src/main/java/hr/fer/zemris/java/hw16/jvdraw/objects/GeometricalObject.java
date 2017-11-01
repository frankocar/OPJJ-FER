package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Models abstract geometrical objects
 * 
 * @author Franko Car
 *
 */
public abstract class GeometricalObject {

	/**
	 * Object name
	 */
	protected String name;
	
	/**
	 * X coordinate of a first mouse click
	 */
	protected int startX;
	/**
	 * Y coordinate of a first mouse click
	 */
	protected int startY;
	/**
	 * X coordinate of a second mouse click
	 */
	protected int endX;
	/**
	 * Y coordinate of a second mouse click
	 */
	protected int endY;
	
	/**
	 * A constructor
	 * 
	 * @param startX starting X coordinate
	 * @param endX ending X coordinate
	 * @param startY starting Y coordinate
	 * @param endY ending Y coordinate
	 * @param name name
	 */
	public GeometricalObject(int startX, int endX, int startY, int endY, String name) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.name = name;
	}
	
	/**
	 * A constructor
	 * 
	 * @param name name
	 */
	public GeometricalObject(String name) {
		this.name = name;
	}
	
	/**
	 * Returns object name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets a new name
	 * 
	 * @param name new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the x coordinate of a first mouse click
	 * 
	 * @return first x coordinate
	 */
	public int getStartX() {
		return startX;
	}
	
	/**
	 * Sets the x coordinate of a first mouse click
	 * 
	 * @param startX first x coordinate
	 */
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	/**
	 * Returns the y coordinate of a first mouse click
	 * 
	 * @return first y coordinate
	 */
	public int getStartY() {
		return startY;
	}
	
	/**
	 * Sets the y coordinate of a first mouse click
	 * 
	 * @param startY first x coordinate
	 */
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	/**
	 * Returns the x coordinate of a second mouse click
	 * 
	 * @return second x coordinate
	 */
	public int getEndX() {
		return endX;
	}
	
	/**
	 * Sets the x coordinate of a second mouse click
	 * 
	 * @param endX second x coordinate
	 */
	public void setEndX(int endX) {
		this.endX = endX;
	}
	
	/**
	 * Returns the y coordinate of a second mouse click
	 * 
	 * @return second y coordinate
	 */
	public int getEndY() {
		return endY;
	}
	
	/**
	 * Sets the y coordinate of a second mouse click
	 * 
	 * @param endY second y coordinate
	 */
	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	/**
	 * Left-most coordinate of an object
	 * 
	 * @return Left-most coordinate
	 */
	public abstract int getMinX();
	/**
	 * Top coordinate of an object
	 * 
	 * @return Top coordinate
	 */
	public abstract int getMinY();
	/**
	 * Right-most coordinate of an object
	 * 
	 * @return Right-most coordinate
	 */
	public abstract int getMaxX();
	/**
	 * Bottom coordinate of an object
	 * 
	 * @return Bottom coordinate
	 */
	public abstract int getMaxY();
	
	/**
	 * Exports the object in a string format, consisting of
	 * object type, coordinates and color information. Space-separated
	 * 
	 * @return String represenation of an object
	 */
	public abstract String export();
	/**
	 * Draws the object on a given graphics
	 * 
	 * @param g graphics
	 */
	public abstract void draw(Graphics2D g);
	/**
	 * Returns a JPanel with all options that can be changed
	 * 
	 * @return JPanel option panel
	 */
	public abstract JPanel getOptions();
	/**
	 * Applies the options set in an option panel
	 */
	public abstract void applyOptions();
	
	@Override
	public String toString() {
		return name;
	}
	
}
