package hr.fer.zemris.java.tecaj.hw04.db;

import java.util.List;

/**
 * An implementation of a {@link IFilter} interface to check
 * if a record satisfies the given query.
 * 
 * @author Franko Car
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * A list of expressions to be evaluated
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * A constructor that will take in a list of conditional expressions
	 * to be evaluated
	 * 
	 * @param expressions A list of conditional expressions
	 * @throws IllegalArgumentException if a given list is undefined
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		if (expressions == null) {
			throw new IllegalArgumentException("List of expressions can't be null");
		}
		
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expr : expressions) {
			IComparisonOperator oper = expr.getOperator();
			IFieldValueGetter getter = expr.getGetter();
			if (!oper.satisfied(expr.getValue(), getter.get(record))) {
				return false;
			}
		}
		
		return true;
	}

}
