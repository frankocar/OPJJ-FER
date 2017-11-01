package hr.fer.zemris.bf.lexer;

/**
 * A class that will take in input text and tokenize it.
 * Every {@code nextToken()} call will generate a new token until EOF is found.
 * 
 * It will generate tokens of type EOF, VARIABLE, CONSTANT, OPERATOR, OPEN_BRACKET and CLOSED_BRACKET
 * as defined in {@link TokenType} enum. In case of an error a {@link LexerException} will be thrown.
 * 
 * Tokens of type VARIABLE must start with a letter and contain only letters, numbers and underscores.
 * 
 * Tokens of type CONSTANT may be "true" or "false". Numeric values 1 and 0 are also accepted, but other
 * numbers aren't.
 * 
 * Tokens of type OPERATOR may be "and", "or", "not", "xor" and may be represented with '*', '+', '!' and ':+:' 
 * respectively.
 * 
 * @author Franko Car
 *
 */
public class Lexer {

	/** Input text data */
	private char[] data;
	/** Current index in dataset */
	private int currentIndex;
	/** Last generated token */
	private Token token;
	
	/** Symbol for open brackets */
	private static final char OPEN_BRACKET = '(';
	/** Symbol for closing brackets */
	private static final char CLOSING_BRACKET = ')';
	
	/**
	 * A constructor that takes in text to be tokenized and
	 * initializes lexer.
	 * 
	 * @param expression Text to tokenize
	 * @throws LexerException When dataset is null
	 */
	public Lexer(String expression) {
		if (expression == null) {
			throw new LexerException("Input data can't be null");
		}
		
		data = expression.toCharArray();
	}
	
	/**
	 * A method that looks for tokens in a given dataset and returns the
	 * first found token.
	 * 
	 * @return Token The following token
	 * @throws LexerException If input string is invalid or contains a number out of range 
	 * 							for a long type.
	 */
	public Token nextToken() {
		if (token != null && token.getTokenType() == TokenType.EOF) {
			throw new LexerException("No tokens available");
		}
		
		skipWhitespace();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (findId()) return token;
		if (findNumeric()) return token;
		if (findBrackets()) return token;
		
		throw new LexerException("Invalid input");		
	}
	
	/**
	 * A method to find brackets and return them as tokens
	 * 
	 * @return true if brackets are found, false otherwise
	 */
	private boolean findBrackets() {
		if (data[currentIndex] == OPEN_BRACKET) {
			token = new Token(TokenType.OPEN_BRACKET, OPEN_BRACKET);
			currentIndex++;
			return true;
		}
		
		if (data[currentIndex] == CLOSING_BRACKET) {
			token = new Token(TokenType.CLOSED_BRACKET, CLOSING_BRACKET);
			currentIndex++;
			return true;
		}
		
		return false;
	}

	/**
	 * A method to find numeric values and return them as tokens.
	 * Only values accepted are 1 and 0, which will be tokenized as 
	 * boolean true and false. If any other number is found, a 
	 * {@link LexerException} will be thrown.
	 * 
	 * @return true if valid numbers are found, false otherwise
	 * @throws LexerException if the number isn't a valid boolean value
	 */
	private boolean findNumeric() {
		if (!Character.isDigit(data[currentIndex])) {
			return false;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);
		
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}
		
		int value;
		try {
			value = Integer.parseInt(sb.toString());
		} catch (NumberFormatException ex) {
			throw new LexerException(String.format("Unexpected number: %s.", sb.toString()), ex);
		}
		
		if (value == 0) {
			token = new Token(TokenType.CONSTANT, false);
			return true;
		}
		
		if (value == 1) {
			token = new Token(TokenType.CONSTANT, true);
			return true;
		}
		
		throw new LexerException(String.format("Unexpected number: %d.", value));		
	}

	/**
	 * A method to find identificators and return them as tokens.
	 * They must start with a letter and contain only letters, numbers and underscores.
	 * 
	 * Logical operators will be classified as OPERATOR type, variable names as VARIABLE type 
	 * and "true" or "false" will be boolean CONSTANTS. Case insensitive.
	 * 
	 * 
	 * @return true if valid tokens are found, false otherwise
	 */
	private boolean findId() {
		
		if (findOperatorSymbol()) {
			return true;
		}
		
		if (!Character.isLetter(data[currentIndex])) {
			return false;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);
		
		
		while (currentIndex < data.length && (Character.isLetterOrDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			sb.append(data[currentIndex++]);
		}
		
		if (isOperator(sb.toString())) {
			token = new Token(TokenType.OPERATOR, sb.toString().toLowerCase());
			return true;
		} else if (isConstant(sb.toString())) {
			token = new Token(TokenType.CONSTANT, sb.toString().toLowerCase().equals("true") ? true : false);
			return true;
		}
		
		token = new Token(TokenType.VARIABLE, sb.toString().toUpperCase());
		return true;
	}

	/**
	 * A method to check if a character is a valid operator symbol.
	 * 
	 * @return true if valid symbol, false otherwise.
	 */
	private boolean findOperatorSymbol() {
		char c = data[currentIndex];
		
		if (!(c == '*' || c == '+' || c == '!'  || c == ':')) {
			return false;
		}
		
		switch (c) {
		case '*':
			token = new Token(TokenType.OPERATOR, "and");
			currentIndex++;
			return true;
		case '+':
			token = new Token(TokenType.OPERATOR, "or");
			currentIndex++;
			return true;
		case '!':
			token = new Token(TokenType.OPERATOR, "not");
			currentIndex++;
			return true;
		case ':':
			if (currentIndex + 2 < data.length && data[currentIndex + 1] == '+' && data[currentIndex + 2] == ':') {
				token = new Token(TokenType.OPERATOR, "xor");
				currentIndex += 3;
				return true;
			}
		default:
			return false;
		}
	}

	/**
	 * A method to check if a string is a valid constant
	 * 
	 * @param string Input string
	 * @return true if constant, false otherwise
	 */
	private boolean isConstant(String string) {
		string = string.toLowerCase();
		
		if (string.equals("true") || string.equals("false")) {
			return true;
		}		
		return false;
	}

	/**
	 * A method to check if a string is a valid operator
	 * 
	 * @param string Input string
	 * @return true if operator, false otherwise
	 */
	private boolean isOperator(String string) {
		string = string.toLowerCase();
		
		if (string.equals("and") || string.equals("xor") || string.equals("or") ||  string.equals("not")) {
			return true;
		}		
		return false;
	}

	/**
	 * A helper method to skip all whitespace
	 */
	private void skipWhitespace() {
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
				continue;
			}
			break;
		}
	}	
}
