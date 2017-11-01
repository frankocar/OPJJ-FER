package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing an unmodifiable complex number. Each method 
 * making a change returns a new object with a resulting number. 
 * 
 * @author Franko Car
 *
 */
public class Complex {

	/** The value of real part */
	private double realPart;
	/** The value of imaginary part */
	private double imaginaryPart;

	/** Complex zero constant */
	public static final Complex ZERO = new Complex(0, 0);
	/** Complex one constant */
	public static final Complex ONE = new Complex(1, 0);
	/** Complex negative one constant */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** Complex imaginary one constant */
	public static final Complex IM = new Complex(0, 1);
	/** Complex negative imaginary one constant */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Constructor creating a zero complex number
	 */
	public Complex() {
		this(0, 0);
	}
	
	/**
	 * A constructor taking real and imaginary parts as arguments
	 * 
	 * @param re Real part
	 * @param im Imaginary part
	 */
	public Complex(double re, double im) {
		this.realPart = re;
		this.imaginaryPart = im;
	}

	/**
	 * Calculates the module of the complex number
	 * 
	 * @return module
	 */
	public double module() {
		return Math.sqrt(realPart * realPart + imaginaryPart * imaginaryPart);
	}
	
	/**
	 * Multiplies another complex number with the one calling the method
	 * 
	 * @param c Reference to the other number
	 * @return New complex number with the result of multiplication
	 */
	public Complex multiply(Complex c) {
		double real = this.realPart * c.realPart - this.imaginaryPart * c.imaginaryPart;
		double imaginary = this.realPart * c.imaginaryPart + this.imaginaryPart * c.realPart;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Divides another complex number with the one calling the method
	 * 
	 * @param c Reference to the other number
	 * @return New complex number with the result of division
	 * @throws IllegalArgumentException Second number is zero, division by zero isn't possible
	 */
	public Complex divide(Complex c) {
		double denominator = c.realPart * c.realPart + c.imaginaryPart * c.imaginaryPart;
		
		if(denominator == 0) {
			throw new IllegalArgumentException("Second number is zero, can't divide with zero");
		}
		
		double real = (this.realPart * c.realPart + this.imaginaryPart * c.imaginaryPart) / denominator;
		double imaginary = (this.imaginaryPart * c.realPart - this.realPart * c.imaginaryPart) / denominator;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Adds another complex number to the one calling the method
	 * 
	 * @param c Reference to the other number
	 * @return New complex number with the result of addition
	 */
	public Complex add(Complex c) {
		double real = this.realPart + c.realPart;
		double imaginary = this.imaginaryPart + c.imaginaryPart;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Subtracts another complex number from the one calling the method
	 * 
	 * @param c Reference to the other number
	 * @return New complex number with the result of subtraction
	 */
	public Complex sub(Complex c) {
		double real = this.realPart - c.realPart;
		double imaginary = this.imaginaryPart - c.imaginaryPart;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Returns a negated complex number
	 * 
	 * @return Negated number
	 */
	public Complex negate() {
		return new Complex(-realPart, -imaginaryPart);
	}
	
	/**
	 * Calculates the n-th power of the complex number 
	 * 
	 * @param n Power to calculate
	 * @return New complex number
	 * @throws IllegalArgumentException If power is negative
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Can't calculate negative power");
		}
		
		
		double angle = Math.atan2(imaginaryPart, realPart);		
		double magnitude = Math.pow(this.module(), n);
		double cosine = Math.cos(n * angle);
		double sine = Math.sin(n * angle);
		
		return new Complex(cosine * magnitude, sine * magnitude);
	}
	
	/**
	 * Calculates the n-th root of the complex number
	 * 
	 * @param n Root degree
	 * @return An array of roots of the required degree
	 * @throws IllegalArgumentException If degree is less or equal than zero
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Can't calculate root with a degree of less or equal than zero");
		}
		
		double angle = Math.atan2(imaginaryPart, realPart);
		double magnitude = Math.pow(this.module(), 1.d/n);
		
		List<Complex> list = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			double cosine = Math.cos((angle + 2.0*i*Math.PI)/n);
			double sine = Math.sin((angle + 2.0*i*Math.PI)/n);
			list.add(new Complex(cosine * magnitude, sine * magnitude));
		}
		
		return list;		
	}
	
	@Override
	public String toString() {
		return String.format("%.3f%s%.3fi", realPart, imaginaryPart < 0 ? "" : "+", imaginaryPart);
	}
	
	/**
	 * A getter for the real part of the complex number
	 * 
	 * @return Real part
	 */
	public double getReal() {
		return realPart;
	}
	
	/**
	 * A getter for the imaginary part of the complex number
	 * 
	 * @return Imaginary part
	 */
	public double getImaginary() {
		return imaginaryPart;
	}
	
}
