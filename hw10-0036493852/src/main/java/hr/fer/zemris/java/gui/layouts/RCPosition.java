package hr.fer.zemris.java.gui.layouts;

/**
 * A class to store position of an element in a table-like grid.
 * Values are starting from 1.
 * 
 * @author Franko Car
 *
 */
public class RCPosition {

	/** Row */
	private int row;
	/** Columnt */
	private int column;
	
	/**
	 * A constructor taking in a row and a column number
	 * 
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		if (row < 1 || column < 1) {
			throw new IllegalArgumentException("Size must be at least 1");
		}
		
		this.row = row;
		this.column = column;
	}
	
	/**
	 * A getter for row number
	 * 
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * A getter for column number
	 * 
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}
	
}
