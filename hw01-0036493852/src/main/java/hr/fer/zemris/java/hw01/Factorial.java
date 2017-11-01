package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A simple program that reads a number in range [1, 20]
 * and calculates its factorial. If the input is invalid, 
 * it will inform the user and ignore the value. It will
 * end when user input is 'kraj'.
 * 
 * @author Franko Car
 *
 */
public class Factorial {

	/**
	 * Main method that starts the program.
	 * Doesn't require any arguments.
	 * 
	 * @param args arguments will be ignored
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.print("Unesite broj > ");
			
			if(sc.hasNextInt()) {
				int current = sc.nextInt();
				if (current < 1 || current > 20) {
					System.out.format("'%d' nije broj u dozvoljenom rasponu.%n", current);
				} else {
					System.out.format("%d! = %d%n", current, calculateFactorial(current));
				}
			} else {
				String input = sc.next();
				if(input.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				} else {
					System.out.format("'%s' nije cijeli broj.%n", input);
				}
			}
		}
		
		sc.close();

	}

	/**
	 * A method that will return a factorial of a given integer.
	 * Returns -1 for numbers less than 0 and greater than 20.
	 * 
	 * @param number A number to calculate factorial of
	 * @return Factorial of a number, type long
	 */
	public static long calculateFactorial(int number) {
		
		if (number < 0 || number > 20) {
			return -1;
		}
		
		long factorial = 1;
				
		for(int i = 1; i <= number; i++) {
			factorial *= i;
		}
				
		return factorial;
	}

}
