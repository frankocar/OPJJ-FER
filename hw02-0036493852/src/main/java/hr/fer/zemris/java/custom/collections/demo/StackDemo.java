package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A program that evaluates RPN expressions.
 * Expression should be given as a command line argument, 
 * with individual elements separated by spaces and enclosed
 * in double quotation marks. It works for operators:
 * 
 * 	+ - addition
 * 	- - subtraction
 * 	/ - division
 * 	* - multiplication
 *  % - modulo
 * 
 * @author Franko Car
 *
 */
public class StackDemo {
	
	/**
	 * A main method that starts the program
	 * 
	 * @param args Expression with individual elements separated by spaces and enclosed in double quotation marks
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Argument format is invalid");
			System.out.println("Make sure your expression is in quotation marks and different elements within are separated by spaces");
			System.exit(1);
		}
		
		String[] input = args[0].split(" ");		
		ObjectStack stack = new ObjectStack();
		
		for(String element : input) {
			try {
				int number = Integer.parseInt(element);
				stack.push(number);
			} catch (NumberFormatException ex) {
				int number2 = (int) stack.pop();
				int number1 = (int) stack.pop();
				
				switch (element) {
				case "+":
					stack.push(number1 + number2);
					break;

				case "-":
					stack.push(number1 - number2);
					break;
					
				case "/":
					stack.push(number1 / number2);
					break;
					
				case "*":
					stack.push(number1 * number2);
					break;
					
				case "%":
					stack.push(number1 % number2);
					break;
					
				default:
					System.out.printf("Unrecognized operator '%s'. %n", element);
					System.exit(1);
				}
			}
		}
		
		if(stack.size() != 1) {
			System.out.println("Expression error");
		} else {
			System.out.printf("Expression evaluates to %d.",stack.pop());
		}
		
		
	}
	
}
