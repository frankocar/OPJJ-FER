package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class representing an unmodifiable complex number. Each method 
 * making a change returns a new object with a resulting number. 
 * 
 * @author Franko Car
 *
 */
public class ComplexNumber {
	
	/**
	 * The value of real part
	 */
	private final double realPart;
	/**
	 * The value of imaginary part
	 */
	private final double imaginaryPart;
	
	/**
	 * A constructor taking real and imaginary parts as arguments
	 * 
	 * @param real Real part
	 * @param imaginary Imaginary part
	 */
	public ComplexNumber(double real, double imaginary) {
		this.realPart = real;
		this.imaginaryPart = imaginary;
	}
	
	/**
	 * A static method returning a new ComplexNumber from a given real part
	 * 
	 * @param real Real part
	 * @return New ComplexNumber object with 0 as imaginary part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * A static method returning a new ComplexNumber from a given imaginary part
	 * 
	 * @param imaginary Imaginary part
	 * @return New ComplexNumber object with 0 as real part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * A static method returning a new ComplexNumber from given magnitude and angle
	 * 
	 * @param magnitude Magnitude of a complex number
	 * @param angle Angle of a complex number
	 * @return New ComplexNumber calculated from given arguments
	 * @throws IllegalArgumentException If magnitude is negative
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException("Magnitude can't be negative");
		}
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
		
	/**
	 * A method that parses given string as a complex number and returns a new ComplexNumber.
	 * It can parse any string containing a real and/or imaginary part given as real numbers, 
	 * with i denoting an imaginary part. 
	 * 
	 * @param input Input string
	 * @return A new complex number from parsed data
	 * @throws IllegalArgumentException When no values can be parsed
	 */
	public static ComplexNumber parse(String input) {
		input = input.replaceAll("\\s", ""); //make sure there aren't any whitespaces between characters
		Pattern pattern = Pattern.compile("([+-]?\\d*(\\.\\d+)?)i?"); //looks for decimal numbers that can start with +, - or nothing and end with 'i' or nothing
		Matcher matcher = pattern.matcher(input);
	
		Double real = 0.d; 
		Double imaginary = 0.d;
		boolean somethingFound = false;
		
		while(matcher.find()) {	
			/*
			 * if the first group(whole expression) ends with an i, 
			 * it's the imaginary part, so we take in a group that contains its value
			 */
			if(matcher.group(0).endsWith("i")) { 
				somethingFound = true;
				/*
				 * if group 0 has an 'i' but group 1 is empty or has a '-'/'+',
				 * it means input is 'i' or '-i'/'+i' so it's handled here
				 */
				if(!matcher.group(1).isEmpty() && !matcher.group(1).equals("-") && !matcher.group(1).equals("+")) { 
					imaginary = Double.parseDouble(matcher.group(1));
				} else if(matcher.group(1).equals("-")) {
					imaginary = -1.d;
				} else {
					imaginary = 1.d;
				}
			} else if(!matcher.group(0).isEmpty()) { //if it doesn't contain an 'i' and it isn't empty, it's real
				real = Double.parseDouble(matcher.group(1));
				somethingFound = true;
			}
		}
		
		if(!somethingFound) {
			throw new IllegalArgumentException("Not a valid complex number");
		}
				
		return new ComplexNumber(real, imaginary);
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
	
	/**
	 * A getter for the magnitude of the complex number
	 * 
	 * @return Magnitude
	 */
	public double getMagnitude() {
		return Math.sqrt(realPart*realPart + imaginaryPart*imaginaryPart);
	}
	
	/**
	 * A getter for the angle of the complex number
	 * 
	 * @return Angle
	 */
	public double getAngle() {
		return Math.atan2(imaginaryPart, realPart);
	}
	
	/**
	 * Adds another complex number to the one calling the method
	 * 
	 * @param otherNumber Reference to the other number
	 * @return New complex number with the result of addition
	 */
	public ComplexNumber add(ComplexNumber otherNumber) {
		double newReal = this.realPart + otherNumber.getReal();
		double newImaginary = this.imaginaryPart + otherNumber.getImaginary();
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Subtracts another complex number from the one calling the method
	 * 
	 * @param otherNumber Reference to the other number
	 * @return New complex number with the result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber otherNumber) {
		double newReal = this.realPart - otherNumber.getReal();
		double newImaginary = this.imaginaryPart - otherNumber.getImaginary();
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Multiplies another complex number with the one calling the method
	 * 
	 * @param otherNumber Reference to the other number
	 * @return New complex number with the result of multiplication
	 */
	public ComplexNumber mul(ComplexNumber otherNumber) {
		double newReal = (this.realPart * otherNumber.getReal() - this.imaginaryPart * otherNumber.getImaginary());
		double newImaginary = (this.imaginaryPart * otherNumber.getReal() + this.realPart * otherNumber.getImaginary());
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Divides another complex number with the one calling the method
	 * 
	 * @param otherNumber Reference to the other number
	 * @return New complex number with the result of division
	 * @throws IllegalArgumentException Second number is zero, division by zero isn't possible
	 */
	public ComplexNumber div(ComplexNumber otherNumber) {
		double denominator = otherNumber.getReal() * otherNumber.getReal() + otherNumber.getImaginary() * otherNumber.getImaginary();
		
		if(denominator == 0) {
			throw new IllegalArgumentException("Second number is zero, can't divide with zero");
		}
		
		double newReal = (this.realPart * otherNumber.getReal() + this.imaginaryPart * otherNumber.getImaginary()) / denominator;
		double newImaginary = (this.imaginaryPart * otherNumber.getReal() - this.realPart * otherNumber.getImaginary()) / denominator;
		
		return new ComplexNumber(newReal, newImaginary);
	}
	
	/**
	 * Calculates the n-th power of the complex number 
	 * 
	 * @param n Power to calculate
	 * @return New complex number, result of the exponentiation
	 * @throws IllegalArgumentException If power is negative
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Can't calculate negative power");
		}
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		
		magnitude = Math.pow(magnitude, n);
		double cosine = Math.cos(n * angle);
		double sine = Math.sin(n * angle);
		
		return new ComplexNumber(cosine * magnitude, sine * magnitude);
	}
		
	/**
	 * Calculates the n-th root of the complex number
	 * 
	 * @param n Root degree
	 * @return An array of roots of the required degree
	 * @throws IllegalArgumentException If degree is less or equal than zero
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("Can't calculate root with a degree of less or equal than zero");
		}
		
		ComplexNumber[] array = new ComplexNumber[n];
		
		double magnitude = this.getMagnitude();
		double angle = this.getAngle();
		
		magnitude = Math.pow(magnitude, 1.d/n);
		for(int i = 0; i < n; i++) {
			double cosine = Math.cos((angle + 2*i*Math.PI)/n);
			double sine = Math.sin((angle + 2*i*Math.PI)/n);
			array[i] = new ComplexNumber(cosine * magnitude, sine * magnitude);
		}
		
		return array;
		
	}
	
	@Override
	public String toString() {
		return String.format("%.3f%s%.3fi", realPart, imaginaryPart < 0 ? "" : "+", imaginaryPart);
	}
	
}
