package hr.fer.zemirs.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.Factorial;

/**
 * Tests for Factorial class
 * 
 * @author Franko Car
 *
 */
public class FactorialTest {

	/**
	 * Factorial of 0
	 */
	@Test
	public void zeroFactorial() {
		long factorial = Factorial.calculateFactorial(0);
		assertEquals(1, factorial);
	}
	
	/**
	 * Factorial of 1
	 */
	@Test
	public void oneFactorial() {
		long factorial = Factorial.calculateFactorial(1);
		assertEquals(1, factorial);
	}
	
	/**
	 * Factorial of 2
	 */
	@Test
	public void twoFactorial() {
		long factorial = Factorial.calculateFactorial(2);
		assertEquals(2, factorial);
	}
	
	/**
	 * Factorial of 5
	 */
	@Test
	public void fiveFactorial() {
		long factorial = Factorial.calculateFactorial(5);
		assertEquals(120, factorial);
	}
	
	/**
	 * Factorial of 10
	 */
	@Test
	public void tenFactorial() {
		long factorial = Factorial.calculateFactorial(10);
		assertEquals(3628800, factorial);
	}
	
	/**
	 * Factorial of 15
	 */
	@Test
	public void fifteenFactorial() {
		long factorial = Factorial.calculateFactorial(15);
		assertEquals(1_307_674_368_000L, factorial);
	}
	
	/**
	 * Factorial of 20
	 */
	@Test
	public void twentyFactorial() {
		long factorial = Factorial.calculateFactorial(20);
		assertEquals(2_432_902_008_176_640_000L, factorial);
	}
	
	/**
	 * Tests what happens when trying to calculate a factorial of a negative number
	 */
	@Test
	public void negativeFactorial() {
		long factorial = Factorial.calculateFactorial(-2);
		assertEquals(-1L, factorial);
	}
	
	/**
	 * Tests what happens when trying to calculate a factorial of a large number
	 */
	@Test
	public void tooLargeFactorial() {
		long factorial = Factorial.calculateFactorial(22);
		assertEquals(-1L, factorial);
	}

}
