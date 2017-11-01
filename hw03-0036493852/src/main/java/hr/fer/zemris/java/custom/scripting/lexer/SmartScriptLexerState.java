package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * An enum with possible SmartScriptLexer states
 * 
 * @author Franko Car
 *
 */
public enum SmartScriptLexerState {
	/**
	 * Basic state, for plain text 
	 */
	BASIC, 
	/**
	 * State for processing tokens within tags
	 */
	IN_TAGS;
}
