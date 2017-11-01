package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class QMC {

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
			
			Pattern pattern = Pattern.compile(
					"^([A-Z0-9]+\\s*\\(([A-Z0-9,\\s]+)\\)\\s*)=" //function name and variables
					+ "\\s*(([01A-Z()+\\-*:!\\s]+)|(\\[([0-9,\\s]+)\\]))?" //minterms
					+ "(\\s*\\|\\s*(([01A-Z()+\\-*:!\\s]+)|(\\[(([0-9,\\s])+)\\])))?$" //don't cares
					, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(input);
			
			if (!matcher.matches()) {
				System.out.println("Error: Invalid function");
				continue;
			}
			
						
			Minimizer m;
			try {
				List<String> variables = parseVariables(matcher);			
				Set<Integer> minterms = parseMinterms(matcher, variables);
				Set<Integer> dontCare = parseDontCares(matcher,variables);
				
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

	/**
	 * A method to parse don't care values
	 * 
	 * @param matcher matcher with input data
	 * @param variables list of variables
	 * @return don't care set
	 */
	private static Set<Integer> parseDontCares(Matcher matcher, List<String> variables) {
		Set<Integer> dontCare = new HashSet<>();
		if (matcher.group(7) != null) {
			if (matcher.group(9) != null) {
				dontCare = Util.toSumOfMinterms(variables, new Parser(matcher.group(9)).getExpression());
			} else {
				for (String num : matcher.group(11).split("\\s*,\\s*")) {
					dontCare.add(Integer.parseInt(num));
				}
			}
		}
		return dontCare;
	}

	/**
	 * A method to parse minterms
	 * 
	 * @param matcher matcher with input data
	 * @param variables list of variables
	 * @return minterm set
	 */
	private static Set<Integer> parseMinterms(Matcher matcher, List<String> variables) {
		Set<Integer> minterms;
		if (matcher.group(4) == null) {
			minterms = new HashSet<>();
			for (String num : matcher.group(6).split("\\s*,\\s*")) {
				minterms.add(Integer.parseInt(num));
			}
		} else {
			minterms = Util.toSumOfMinterms(variables, new Parser(matcher.group(3)).getExpression());
		}
		return minterms;
	}

	/**
	 * A method to parse variable list
	 * 
	 * @param matcher matcher with input data
	 * @return variable list
	 */
	private static List<String> parseVariables(Matcher matcher) {
		return Arrays.asList(matcher.group(2).toUpperCase().split("\\s*,\\s*"));
	}
	
}
