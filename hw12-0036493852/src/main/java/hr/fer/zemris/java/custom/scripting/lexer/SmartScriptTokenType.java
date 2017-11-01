package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An enum with possible SmartScriptToken types
 * 
 * @author Franko Car
 *
 */
public enum SmartScriptTokenType {

	/**
	 * Variable type
	 */
	VARIABLE, 
	/**
	 * Function type
	 */
	FUNCTION, 
	/**
	 * Operator type
	 */
	OPERATOR, 
	/**
	 * Tag type
	 */
	TAG, 
	/**
	 * Text type
	 */
	TEXT, 
	/**
	 * End of file token
	 */
	EOF, 
	/**
	 * Integer number type
	 */
	INTEGER, 
	/**
	 * Double number type
	 */
	DOUBLE, 
	/**
	 * String type
	 */
	STRING; 
	
}
