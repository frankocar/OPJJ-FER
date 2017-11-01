package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Class representing a complex polynomial represented
 * in a form of root terms. Objects are immutable, meaning that
 * every method will return a new object.
 * 
 * @author Franko Car
 *
 */
public class ComplexRootedPolynomial {
	
	/** An array of root null points */
	private Complex[] roots;
	
	/**
	 * A constructor taking in an array of roots and creating a polynomial.
	 * Roots can't be null.
	 * 
	 * @param roots
	 * @throws IllegalArgumentException if factor array is null
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Roots mustn't be null");
		}
		
		this.roots = Arrays.copyOf(roots, roots.length);
	}
	
	/**
	 * Calculates the value of the polynomial at a given point
	 * 
	 * @param z given polynomial
	 * @return complex value
	 * @throws IllegalArgumentException if the given polynomial is null
	 */
	public Complex apply(Complex z) {
		if (z == null) {
			throw new IllegalArgumentException("Given number mustn't be null");
		}
		
		if (roots.length == 0) {
			return Complex.ZERO;
		}
		
		Complex result = Complex.ONE;
		
		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		
		return result;
	}
	
	
	/**
	 * Converts ComplexRootedPolynomial to ComplexPolynomial
	 * 
	 * @return ComplexPolynomial representation
	 */
	public ComplexPolynomial toComplexPolynom() {
		if (roots.length == 0) {
			return new ComplexPolynomial();
		}
		
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE, roots[0].negate());
		for (int i = 1; i < roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, roots[i].negate()));
		}
		
		return result;
	}
	
	/**
	 * Finds the root closest to the given complex number within a given treshold.
	 * Returns -1 if none are found.
	 * 
	 * @param z given complex number
	 * @param treshold treshold
	 * @return index or -1 if none are found
	 * @throws IllegalArgumentException if a given number is null or treshold is negative
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		if (z == null) {
			throw new IllegalArgumentException("Given number mustn't be null");
		}
		
		if (treshold < 0) {
			throw new IllegalArgumentException("Treshold can't be negative");
		}
		
		int index = -1;
		double max = treshold;
		
		for (int i = 0; i < roots.length; i++) {
			double distance = z.sub(roots[i]).module();
			if (distance < max) {
				max = distance;
				index = i;
			}
		}
		
				
		return index == -1 ? -1 : index + 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (Complex root : roots) {
			if (root.module() == 0) {
				continue;
			}
			
			sb.append("(z-(" + root + "))");
		}
		
		return sb.toString();
	}
	
}
