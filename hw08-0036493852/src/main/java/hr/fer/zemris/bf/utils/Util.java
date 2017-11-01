package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;

/**
 * A utility class for expression operations
 * 
 * @author Franko Car
 *
 */
public class Util {

	/**
	 * A method that will consume each node within an expression with a given consumer
	 * 
	 * @param variables List of variables in an expression
	 * @param consumer Consumer
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		int len = variables.size();		
		for (int i = 0, n = (int) Math.pow(2, variables.size()); i < n; i++) {
			
			boolean[] binary = new boolean[len];			
		    for (int j = 0; j < len; j++) {
		        binary[len - 1 - j] = (1 << j & i) != 0;
		    }
		    
		    consumer.accept(binary);
		}
	}
	
	
	/**
	 * A method that will return a set of inputs for which a given expression results in a requested value
	 * 
	 * @param variables List of variables in an expression
	 * @param expression Expression to evaluate
	 * @param expressionValue requested value
	 * @return Set of boolean arrays which result in a correct result
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {		
		ExpressionEvaluator eval = new ExpressionEvaluator(variables);
		
		Set<boolean[]> filtered = new TreeSet<>((x, y) -> {
			for (int i = 0; i < x.length; i++) {
				if (x[i] == y[i])
					continue;

				if (x[i] == false) {
					return -1;
				} else {
					return 1;
				}
			}
			return 0;
		});
		
		Util.forEach(
				variables,
				values -> {
					eval.setValues(values);
					expression.accept(eval);
					if (eval.getResult() == expressionValue) {
						filtered.add(values);
					}
				}
		);
		
		return filtered;
	}
	
	/**
	 * A method to convert boolean array to integer value
	 * 
	 * @param values boolean array of values to convert
	 * @return int value of binary input
	 */
	public static int booleanArrayToInt(boolean[] values) {
		int n = 0;
		
		for (int i = 0; i < values.length; ++i) {
		    n = (n << 1) + (values[i] ? 1 : 0);
		}
		
		return n;
	}
	
	/**
	 * A method that will return sum of minterms of a given expression
	 * 
	 * @param variables List of variables in an expression
	 * @param expression Expression to evaluate
	 * @return Set of rows that are minterms
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return findMintermsAndMaxterms(variables, expression, true);
	}
	
	/**
	 * A method that will return sum of maxterms of a given expression
	 * 
	 * @param variables List of variables in an expression
	 * @param expression Expression to evaluate
	 * @return Set of rows that are maxterms
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return findMintermsAndMaxterms(variables, expression, false);
	}
	
	/**
	 * A method that will return a set of row indices that evaluate to be a minterm or a maxterm
	 * 
	 * @param variables List of variables in an expression
	 * @param expression Expression to evaluate
	 * @param minterm true if searching for minterms, false for maxterms
	 * @return Set of rows that are minterms or maxterms
	 */
	private static Set<Integer> findMintermsAndMaxterms(List<String> variables, Node expression, boolean minterm) {
		Set<Integer> results = new TreeSet<>();
		
		for (boolean[] array : filterAssignments(variables, expression, minterm)) {
			results.add(booleanArrayToInt(array));
		}
		
		return results;
	}
	
	/**
	 * A method that will take in an integer and return its binary value as a byte array.
	 * It will return the requested number of digits, and if the binary representation is 
	 * longer than the requested array length, information will be lost. Negative numbers 
	 * will be returned as two's complements.
	 * 
	 * @param x Number to convert
	 * @param n Length of array
	 * @return byte array with each element representing a single bit
	 */
	public static byte[] indexToByteArray(int x, int n) {
		boolean negative = false;
		if (x < 0) {
			negative = true;
			x *= -1;
		}
		
		List<Byte> binary = new ArrayList<>();
		while (x >= 2) {
			binary.add((byte) (x % 2));
			x /= 2;
		}
		
		binary.add(((byte) x));
		
		byte[] bytes = new byte[n];
		for (int i = 0; i < n && i < binary.size(); i++) {
			bytes[bytes.length - 1 - i] = binary.get(i);
		}
		
		if (negative) {
			//ones complement
			for (int i = 0; i < n; i++) {
				bytes[i] = bytes[i] == 0 ? (byte) 1 : (byte) 0;
			}
			
			//two's complement
			for (int i = n - 1; i >= 0; i--) {
				if (bytes[i] == 1) {
					bytes[i] = (byte) 0;
				} else {
					bytes[i] = (byte) 1;
					break;
				}
			}
		}
		
		return bytes;
	}
		
}
