package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program calculates the area and the perimeter of a rectangle using
 * the two provided arguments. Arguments can be provided either from command line
 * or from the system input while program is running.
 * 
 * @author Franko Car
 * 
 */
public class Rectangle {

	/**
	 * Main method, prints out area and perimeter of a rectangle with given
	 * parameters.
	 * @param args command line arguments, should contain width and height or nothing
	 */
	public static void main(String[] args) {
		double width = 0, height = 0;
		
		if(args.length != 2 && args.length != 0){
			System.out.println("Potrebno je unesti 2 argumenta, širinu i visinu");
			System.exit(1);
		}
		
		if(args.length == 2){
			try {
				width = Double.parseDouble(args[0].trim());
				height = Double.parseDouble(args[1].trim());
				
				if(width < 0 || height < 0) {
					System.out.println("Argument je negativna vrijednost");
					System.exit(1);
				}
			} catch(NumberFormatException ex) {
				System.out.println("Uneseni argumenti nisu realni brojevi");
				System.out.format("Uneseni podatci su '%s' i '%s'.%n", args[0], args[1]);
				System.exit(1);
			}
		} else {
			Scanner sc = new Scanner(System.in);
			width = input(sc, "širinu");
			height = input(sc, "visinu");
			sc.close();
		}
		
		System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.%n",
				width, height, area(width, height), perimeter(width, height));
	}
	
	/**
	 * Reads user input using a provided scanner
	 * until a positive double value is entered. 
	 * 
	 * @param element String describing the expected value 
	 * @param sc Scanner used to read user input
	 * @return Returns user input as a double.
	 */
	private static double input(Scanner sc, String element){
		double number = 0;
		String input;		
		
		while(true) {
			System.out.format("Unesite %s > ", element);
			input = sc.nextLine();
			try {
				number = Double.parseDouble(input);
				if(number <= 0) {
					System.out.println("Unijeli ste negativnu vrijednost");
				} else {
					break;
				}
			} catch(NumberFormatException ex) {
				System.out.format("'%s' se ne može protumačiti kao broj.%n", input);
			}			
		}
				
		return number;
	}
	
	/**
	 * Calculates the area of a rectangle using it's width and height as arguments.
	 * Doesn't work with negative numbers.
	 * 
	 * @param width Width of a rectangle
	 * @param height Height of a rectangle
	 * @return The area of a rectangle
	 */
	public static double area(double width, double height){
		return width * height;
	}
	
	/**
	 * Calculates the perimeter of a rectangle using it's width and height as arguments.
	 * Doesn't work with negative numbers.
	 * 
	 * @param width Width of a rectangle
	 * @param height Height of a rectangle
	 * @return The perimeter of a rectangle
	 */
	public static double perimeter(double width, double height){
		return 2*(width + height);
	}
	
}
