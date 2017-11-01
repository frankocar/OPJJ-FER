package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.parser.ParserException;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.Util;

/**
 * A program that will read boolean functions and return their minimal forms.
 * Input must be given as a function in form <code>function_name(variable_list) = minterms | dont_care</code>
 * Function name can be any word or a letter, variable list must be comma separated list of all
 * variables used in a function. Minterms and don't care values can be given as an expression 
 * readable by {@link Parser} or as a list of terms enclosed in brackets and separated by commas.
 * Don't care values aren't required. 
 * 
 * To terminate the program, input "quit".
 * 
 * @author Franko Car
 *
 */
public class QMC2 {

	/**
	 * Main method
	 * 
	 * @param args aren't required
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("> ");
			String input = sc.nextLine().trim();
			
			if (input.toLowerCase().equals("quit")) {
				break;
			}
			
			String[] split = input.split("=");
			
			if (split.length != 2) {
				System.out.println("Error: Invalid function");
				continue;
			}
			
			if (!split[0].contains("(") || !split[0].contains(")")) {
				System.out.println("Error: Invalid function");
				continue;
			}
			
			List<String> variables = Arrays.asList(split[0].substring(split[0].indexOf("(") + 1, split[0].indexOf(")")).toUpperCase().split("\\s*,\\s*"));
			
			split = split[1].split("\\|");
			
			boolean hasDontCares = split.length > 1 ? true : false;
			Set<Integer> minterms = new HashSet<>();
			if (split[0].trim().startsWith("[")) {
				String tmp = split[0].substring(split[0].indexOf("[") + 1, split[0].indexOf("]"));
				for (String num : tmp.split("\\s*,\\s*")) {
					minterms.add(Integer.parseInt(num));
				} 
			} else {
				minterms = Util.toSumOfMinterms(variables, new Parser(split[0].trim()).getExpression());
			}
			
			Set<Integer> dontCare = new HashSet<>();
			
			if (hasDontCares) {
				if (split[1].trim().startsWith("[")) {
					String tmp = split[1].substring(split[1].indexOf("[") + 1, split[1].indexOf("]"));
					for (String num : tmp.split("\\s*,\\s*")) {
						dontCare.add(Integer.parseInt(num));
					} 
				} else {
					dontCare = Util.toSumOfMinterms(variables, new Parser(split[1].trim()).getExpression());
				}
			}
						
			Minimizer m;
			try {			
				m = new Minimizer(minterms, dontCare, variables);
			} catch (IllegalArgumentException | IllegalStateException 
					| LexerException | ParserException ex) {
				System.out.println("Error: " + ex.getMessage());
				continue;
			}
			
			int i = 1;
			for (String string : m.getMinimalFormsAsString()) {
				System.out.println(i++ + ". " + string);
			}
		}
		
		System.out.println("Terminating... Goodbye");
		sc.close();
	}
	
}
