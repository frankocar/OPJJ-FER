package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * An interface defining a strategy to check if given values 
 * are in the requested relation
 * 
 * @author Franko Car
 *
 */
public interface IComparisonOperator {

	/**
	 * Returns true if the operator is satisfied
	 * 
	 * @param value1 first value
	 * @param value2 second value
	 * @return True if satisfied, false if not
	 */
	public boolean satisfied(String value1, String value2);
	
}
