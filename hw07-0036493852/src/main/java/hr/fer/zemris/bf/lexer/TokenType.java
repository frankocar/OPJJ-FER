package hr.fer.zemris.bf.lexer;

/**
 * An enum with possible token types
 * 
 * @author Franko Car
 *
 */
public enum TokenType {

	/** end of file token */
	EOF,
	/** variable token */
	VARIABLE,
	/** constant token */
	CONSTANT,
	/** operator token */
	OPERATOR,
	/** open bracket token */
	OPEN_BRACKET,
	/** closing bracket token */
	CLOSED_BRACKET
	
}
