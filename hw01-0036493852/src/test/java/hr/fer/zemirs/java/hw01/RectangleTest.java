package hr.fer.zemirs.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.Rectangle;

/**
 * Tests for Rectangle class
 * 
 * @author Franko Car
 *
 */
public class RectangleTest {

	/**
	 * Maximum delta for a double
	 */
	private static final double DELTA = 1E-8;
	
	/**
	 * Area of a rectangle with a 0-length side
	 */
	@Test
	public void areaIsZero() {
		double area = Rectangle.area(0, 5);
		assertEquals(0, area, DELTA);
	}
	
	/**
	 * Area of a 5x5 rectangle
	 */
	@Test
	public void area() {
		double area = Rectangle.area(5, 5);
		assertEquals(25, area, DELTA);
	}
	
	/**
	 * Perimeter of a rectangle with a 0-length side
	 */
	@Test
	public void perimeterIsZero() {
		double area = Rectangle.perimeter(0, 0);
		assertEquals(0, area, DELTA);
	}
	
	/**
	 * Perimeter of a 5x5 rectangle
	 */
	@Test
	public void perimeter() {
		double area = Rectangle.perimeter(5, 5);
		assertEquals(20, area, DELTA);
	}
	
	

}
