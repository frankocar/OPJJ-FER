package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * A simple program to test ComplexNumber class. Does mathematical
 * operations on complex numbers and should output "-1.618+0.069i"
 * 
 * @author Marko Čupić
 *
 */
public class ComplexDemo {

	/**
	 * Method that starts a program
	 * 
	 * @param args none needed
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);

	}

	
}
