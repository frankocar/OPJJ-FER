package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * A class that will return the proper strategy implementation for a requested operator
 * 
 * @author Franko Car
 *
 */
public class ComparisonOperators {
	
	/**
	 * Implementation for the less then operator - '<'
	 */
	public static final IComparisonOperator LESS = ((value2, value1) -> value1.compareTo(value2) < 0);
	/**
	 * Implementation for the less or equals operator - '<='
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = ((value2, value1) -> value1.compareTo(value2) <= 0);
	/**
	 * Implementation for the greater then operator - '>'
	 */
	public static final IComparisonOperator GREATER = ((value2, value1) -> value1.compareTo(value2) > 0);
	/**
	 * Implementation for the greater or equals operator - '>='
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = ((value2, value1) -> value1.compareTo(value2) >= 0);
	/**
	 * Implementation for the equals operator - '='
	 */
	public static final IComparisonOperator EQUALS = ((value2, value1) -> value1.equals(value2));
	/**
	 * Implementation for the doesn't equal operator - '!='
	 */
	public static final IComparisonOperator NOT_EQUALS = ((value2, value1) -> !value1.equals(value2));
	/**
	 * Implementation for the 'LIKE' keyword
	 * 
	 * @throws IllegalArgumentException when more than one wildcard character '*' is given.
	 */
	public static final IComparisonOperator LIKE = ((value2, value1) -> {
		if (value2.indexOf('*') != value2.lastIndexOf('*')) {
			throw new IllegalArgumentException("Only one wildcard allowed: '" + value2 + "'.");
		}
		
		return value1.matches(value2.replace("*", ".*"));
	});
	
	
	
	
}
