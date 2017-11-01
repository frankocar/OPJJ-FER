package hr.fer.zemris.java.tecaj.hw04.db;

/**
 * A class used to store every conditional expression of a query in
 * an appropriate format. It stores getter and comparison strategies 
 * and the value to compare against given in a query.
 * 
 * @author Franko Car
 *
 */
public class ConditionalExpression {

	/**
	 * Getter strategy for a requested field
	 */
	private IFieldValueGetter getter;
	/**
	 * Value given in a query
	 */
	private String value;
	/**
	 * Operator given in a query
	 */
	private IComparisonOperator operator;
	
	/**
	 * A constructor taking in the getter and comparison strategies and the value
	 * 
	 * @param getter Getter strategy
	 * @param value Value
	 * @param operator Operator strategy
	 */
	public ConditionalExpression(IFieldValueGetter getter, String value, IComparisonOperator operator) {
		super();
		this.getter = getter;
		this.value = value;
		this.operator = operator;
	}
	
	/**
	 * A getter for the getter strategy
	 * 
	 * @return {@link IFieldValueGetter} getter
	 */
	public IFieldValueGetter getGetter() {
		return getter;
	}

	/**
	 * A getter for the value
	 * 
	 * @return String value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * A getter for the operator
	 * 
	 * @return {@link IComparisonOperator} operator
	 */
	public IComparisonOperator getOperator() {
		return operator;
	}
	
	
	
	
}
