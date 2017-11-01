package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Class representing a complex polynomial represented
 * in a form of power series. Objects are immutable, meaning that
 * every method will return a new object.
 * 
 * @author Franko Car
 *
 */
public class ComplexPolynomial {

	/** Array of factors */
	private Complex[] factors;

	/**
	 * A constructor taking in an array of factors and creating a polynomial.
	 * Factors can't be null.
	 * 
	 * @param factors
	 * @throws IllegalArgumentException if factor array is null
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Factors mustn't be null");
		}
		
		this.factors = Arrays.copyOf(factors, factors.length);
	}
	
	/**
	 * Returns polynomial order
	 * 
	 * @return order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies two polynomials
	 * 
	 * @param p other polynomial
	 * @return resulting polynomial
	 * @throws IllegalArgumentException if the given polynomial is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		if (p == null) {
			throw new IllegalArgumentException("Other polynomial can't be null");
		}
		
		Complex[] newFactors = new Complex[this.factors.length + p.factors.length - 1];
				
		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {				
				if (newFactors[i + j] == null) {
					newFactors[i + j] = new Complex();
				}
				
				newFactors[i + j] = newFactors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Derives a polynomial
	 * 
	 * @return derivation
	 */
	public ComplexPolynomial derive() {
		Complex[] derivation = new Complex[order()];
		
		for (int i = 0; i < derivation.length; i++) {
			derivation[i] = factors[i].multiply(new Complex(order() - i, 0));
		}
		
		return new ComplexPolynomial(derivation);
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
			throw new IllegalArgumentException("Argument can't be null");
		}
		
		Complex result = new Complex();
		int order = order();
		
		for (Complex c : factors) {
			result = result.add(z.power(order).multiply(c));
			order--;
		}
		
		return result;
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int order = order();
		for (Complex c : factors) {
			sb.append("(" + c.toString() + ")");

			if (order > 0) {
				sb.append(" * Z^" + order + " + ");
				order--;
			}
		}

		
		return sb.toString();
	}
	
	/**
	 * A getter for factor array
	 * 
	 * @return factor array
	 */
	public Complex[] getFactors() { //required for junit tests
		return factors;
	}
	
}
