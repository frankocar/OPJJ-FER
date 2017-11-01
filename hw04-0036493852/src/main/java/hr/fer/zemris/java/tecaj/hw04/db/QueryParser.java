package hr.fer.zemris.java.tecaj.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to parse a query from a given string.
 * 
 * Only allowed relation between different expressions is 'and'
 * which isn't case sensitive. All other parts are case sensitive.
 * 
 * Allowed attributes are 'firstName', 'lastName', and 'jmbag'.
 * Every value must be given as a string.
 * Allowed operators are '<', '<=', '>', '>=', '=', '!=' and 'LIKE'.
 * Like operator allows for one char to be replaced with a wildcard '*'
 * that will match one or more of any char.
 * 
 * @author Franko Car
 *
 */
public class QueryParser {

	/**
	 * A list of separate queries
	 */
	private List<ConditionalExpression> queries;
	
	/**
	 * A constructor taking in a given line to parse.
	 * 'query' from the start must be removed beforehand.
	 * 
	 * @param query
	 * @throws IllegalArgumentException if a given query is null
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new IllegalArgumentException("query can't be null");
		}
		
		queries = new ArrayList<>();
		parseString(query);
	}
	
	/**
	 * A method to start parsing a given string, it will
	 * split it and delegate foward
	 * 
	 * @param query Query to parse
	 */
	private void parseString(String query) {
		query = query.trim();
		String[] expressions = query.split("(?i)\\s+and\\s+");
		
		for (String expression : expressions) {
			queries.add(parseExpression(expression));
		}
		
	}
	
	/**
	 * A method that will parse a single expression to a {@link ConditionalExpression}
	 * 
	 * @param expression Expression to be parsed
	 * @return Parsed {@link ConditionalExpression}
	 * @throws IllegalArgumentException When the given query is invalid
	 */
	public ConditionalExpression parseExpression(String expression) {  		//public just for junit testing
		expression = expression.trim();
		Pattern pattern = Pattern.compile(
				"^(firstName|lastName|jmbag)\\s*(<|>|<=|>=|=|!=|LIKE)\\s*\"([^\"]+)\"\\s*$"
				);
		
		
		Matcher matcher = pattern.matcher(expression);
		
		if (matcher.find()) {
			IFieldValueGetter getter = findAttribute(matcher.group(1));
			IComparisonOperator operator = findOperator(matcher.group(2));
			String value = matcher.group(3);
			
			return new ConditionalExpression(getter, value, operator);
		} else {
			throw new IllegalArgumentException("Invalid query");
		}
	}

	/**
	 * A method to check the type of a given operator
	 * 
	 * @param operator Operator to be checked
	 * @return {@link IComparisonOperator} with appropriate strategy
	 * @throws IllegalArgumentException when unsupported operator is given
	 */
	private IComparisonOperator findOperator(String operator) {
		switch (operator) {
		case "<":
			return ComparisonOperators.LESS;
		case ">":
			return ComparisonOperators.GREATER;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new IllegalArgumentException("Invalid operator");
		}
		
	}

	/**
	 * A method to check the type of a given attribute
	 * 
	 * @param attribute Attribute to be checked
	 * @return {@link IFieldValueGetter} with appropriate strategy
	 * @throws IllegalArgumentException when unsupported attribute is given
	 */
	private IFieldValueGetter findAttribute(String attribute) {
		if (attribute.toLowerCase().equals("lastname")) {
			return FieldValueGetters.LAST_NAME;
		}
		
		if (attribute.toLowerCase().equals("firstname")) {
			return FieldValueGetters.FIRST_NAME;
		}
		
		if (attribute.toLowerCase().equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		}
		
		throw new IllegalArgumentException("Invalid attribute");
	}

	/**
	 * Check if the current query is direct, size must be 1, 
	 * attribute must be JMBAG and the operator must be '='
	 * 
	 * @return True if direct
	 */
	public boolean isDirectQuery() {
		if (queries.size() != 1) {
			return false;
		}
		
		if (queries.get(0).getGetter() != FieldValueGetters.JMBAG) {
			return false;
		}
		
		if (queries.get(0).getOperator() != ComparisonOperators.EQUALS) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns the JMBAG value of a direct query
	 * 
	 * @return String JMBAG
	 * @throws IllegalStateException If the query isn't direct
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Tried using a direct query request on indirect query");
		} 
		
		return queries.get(0).getValue();
		
	}
	
	/**
	 * A getter for the list of {@link ConditionalExpression} in a current querry
	 * 
	 * @return List<ConditionalExpression>
	 */
	public List<ConditionalExpression> getQuery() {
		return queries;
		
	}
	
}
