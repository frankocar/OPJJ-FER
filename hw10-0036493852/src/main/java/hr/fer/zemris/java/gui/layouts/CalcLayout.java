package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A layout for a simple calculator. It is organized in a grid
 * with 5 rows and 7 columns with a space for display occupying
 * first 5 elements, from left to right, in the first row.
 * 
 * @author Franko Car
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	/** Number of rows */
	public static final int ROWS = 5;
	/** Number of columns */
	public static final int COLUMNS = 7;
	
	/** Gap between elements */
	private int gap;
	/** Array of components used */	
	private Component[][] components;
	
	/**
	 * Default constructor, no gap between the elements 
	 */
	public CalcLayout() {
		this(0);
	}	

	/**
	 * A constructor with a parameter that defines gap size
	 * 
	 * @param gap desired gap between elements
	 */
	public CalcLayout(int gap) {
		if (gap < 0) {
			throw new IllegalArgumentException("Components shouldn't overlap");
		}
		
		this.components = new Component[ROWS][COLUMNS];
		this.gap = gap;
	}
	
	
	/** 
	 * Unused, deprecated 
	 */
	@Override
	public void addLayoutComponent(String string, Component component) {
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		int compHeight = (parent.getHeight() - ins.left - ins.right) / ROWS;
		int compWidth = (parent.getWidth() - ins.top - ins.bottom) / COLUMNS;
				
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] == null) {
					continue;
				}
				
				Component x = components[i][j];				
				x.setLocation(compWidth * j + gap, compHeight * i + gap);
				
				if (i == 0 && j == 0) {
					x.setSize(compWidth * 5 - gap, compHeight - gap);
					j += 4;
				} else {
					x.setSize(compWidth - gap, compHeight - gap);
				}
				x.setVisible(true);
				
			}		
		}
	}

	@Override
	public void removeLayoutComponent(Component component) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] != null && components[i][j].equals(component)) {
					components[i][j] = null;
				}				
			}
		}

	}
	
	@Override
	public Dimension minimumLayoutSize(Container container) {
		return getLayoutSize(container, Math::max, Component::getMinimumSize);
	}

	@Override
	public Dimension preferredLayoutSize(Container container) {
		return getLayoutSize(container, Math::max, Component::getPreferredSize);
	}

	@Override
	public Dimension maximumLayoutSize(Container container) {
		return getLayoutSize(container, Math::min, Component::getMaximumSize);
	}
	
	/**
	 * A generic method to get minimum, maximum or preferred layout size.
	 * 
	 * @param container to check
	 * @param compare A BiFunction implementation to check for minimums and maximums
	 * @param operation Function implementation to request desired parameter
	 * @return Dimension of desired layout
	 */
	private Dimension getLayoutSize(Container container, BiFunction<Integer, Integer, Integer> compare, Function<Component, Dimension> operation) {
		int height = 0;
		int width = 0;
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (components[i][j] == null) {
					continue;
				}
				
				Dimension dimension = operation.apply(components[i][j]);
				if (dimension == null) {
					continue;
				}
				
				height = compare.apply(height, dimension.height);
				width = compare.apply(width, dimension.width);
				
				if (i == 0 && j == 0) {
					width *= 5;
					j += 4;
				}			
			}
		}
		
		width *= COLUMNS;		
		height *= ROWS;
		
		Insets ins = container.getInsets();
		width += ins.left + ins.right;
		height += ins.top + ins.bottom;
		
		return new Dimension(width, height);		
	}

	@Override
	public void addLayoutComponent(Component component, Object constraints) {
		RCPosition position;
		if (constraints instanceof String) {
			position = parsePosition((String) constraints);
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Invalid constraint parameter");
		}
		
		if (!isValidPosition(position)) {
			throw new IllegalArgumentException("Invalid position");
		}
		
		components[position.getRow() - 1][position.getColumn() - 1] = component;

	}

	/**
	 * A method to parse a string to RCPosition object.
	 * "x,y" format is expected.
	 * 
	 * @param string to parse
	 * @return RCPosition object
	 */
	private RCPosition parsePosition(String string) {
		string = string.replaceAll("\\s+", "");
		
		String[] parts = string.split(",");
		if (parts.length != 2) {
			throw new IllegalArgumentException("Invalid string describing a constraint");
		}
		
		try {
			int row = Integer.parseInt(parts[0]);
			int col = Integer.parseInt(parts[1]);
			return new RCPosition(row, col);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Given number isn't a valid integer: " + ex.getMessage(), ex);
		}
		
	}

	/**
	 * A simple method to check whether a position is valid and available
	 * 
	 * @param position to check
	 * @return boolean validity
	 */
	private boolean isValidPosition(RCPosition position) {
		int row = position.getRow();
		int col = position.getColumn();
		
		if (row > ROWS || row < 1 || col > COLUMNS || col < 1) {
			return false;
		}
		
		if (components[row -1][col - 1] != null) {
			return false;
		}
		
		if (row == 1 && col >= 2 && col <= 5) {
			return false;
		}
		
		return true;		
	}

	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0.5f;
	}

	/** 
	 * Unused, deprecated 
	 */
	@Override
	public void invalidateLayout(Container container) {
	}

}
