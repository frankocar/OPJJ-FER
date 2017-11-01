package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A program to show a fractal calculated using Newton-Raphson iteration-based 
 * method. It's built using given roots of a complex polynom. Input must be in
 * form "a+ib" with a and b being real and imaginary constants that can be left out.
 * 
 * @author Franko Car
 *
 */
public class Newton {

	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> list = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		for (int i = 1; true; i++) {
			System.out.print("Root " + i + "> ");
			String input = sc.nextLine().trim();
			
			if (input.toLowerCase().equals("done")) {
				if (i <= 2) {
					System.out.println("At least two roots required, terminating");
					System.exit(1);
				}
				break;
			}
			
			Complex number = null;
			try {
				number = parseComplex(input);
				System.out.println(number);
			} catch (IllegalArgumentException ex) {
				System.out.println("Invalid complex number, please try again or enter 'done' to finish");
				i--;
				continue;
			}
			
			list.add(number);			
		}
		
		System.out.println("Image of a fractal will appear shortly. Thank you.");
		sc.close();
		
		ComplexRootedPolynomial roots = new ComplexRootedPolynomial(list.toArray(new Complex[list.size()]));
		FractalViewer.show(new FractalProducerImpl(roots));
		
	}
	
	/**
	 * A method to parse complex number strings
	 * 
	 * @param input String representing a complex number in a valid form
	 * @return parsed Complex number
	 */
	public static Complex parseComplex(String input) {
		input = input.replaceAll("\\s", "");
		Pattern pattern = Pattern.compile("^([+-]?[\\d.]+)?([+-])?((?<!\\d)i([\\d.]*))?$");
		Matcher matcher = pattern.matcher(input);
	
		Double real = 0.d; 
		Double imaginary = 0.d;
		boolean somethingFound = false;
		
		while(matcher.find()) {	
			
			if (matcher.group(1) != null) {
				real = Double.parseDouble(matcher.group(1).replaceAll("\\+", ""));
				somethingFound = true;
			}
			
			if (matcher.group(3) != null) {
				if (matcher.group(4) == null || matcher.group(4).isEmpty()) {
					imaginary = 1.d;
					somethingFound = true;
				} else {
					imaginary = Double.parseDouble(matcher.group(4));
					somethingFound = true;
				}
				if (matcher.group(2) != null && matcher.group(2).equals("-")) {
					imaginary *= -1;
				}
			}			
		}
		
		if(!somethingFound) {
			throw new IllegalArgumentException("Not a valid complex number");
		}
		
		return new Complex(real, imaginary);
	}
	
}
