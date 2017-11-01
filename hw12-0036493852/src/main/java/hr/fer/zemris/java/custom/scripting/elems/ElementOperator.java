package hr.fer.zemris.java.custom.scripting.elems;

/**
 * An element subclass used to store operators.
 * Can be '+', '-', '*', '/', '^'
 * 
 * @author Franko Car
 *
 */
public class ElementOperator extends Element {

	/**
	 * Operator symbol
	 */
	private final String symbol;
	
	@Override
	public String asText() {
		return symbol;
	}
	
	/**
	 * A constructor
	 * 
	 * @param symbol String symbol of an operator
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Operator getter
	 * 
	 * @return operator String
	 */
	public String getSymbol() {
		return symbol;
	}
}
