package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A class that will take in input text and tokenize it.
 * Every {@code nextToken()} call will generate a new token until EOF is found.
 * {@code getToken()} call will return the last found token.
 * A SmartScriptLexer can have two states as defined by SmartScriptLexerType enum, 
 * 'basic' and 'in tags'.
 * 
 * In basic state, lexer will recognize everything as plain text, with no special
 * characters and escape sequences just for opening tags and escape char itself.
 * 
 * When tokenizing tags, it will recognize strings marked by opening and closing
 * double quotation marks, variable names that can contain letters, digits and '_', 
 * but must start with a letter or '_', functions that start with '@', operators 
 * '+', '-', '*', '/' and '^', as well as tag names starting with '='.
 * 
 * LexerException will be thrown if there is an error in the given dataset, and 
 * IllegalArgumentException will be thrown when the initial parameters aren't supported.
 * 
 * @author Franko Car
 *
 */
public class SmartScriptLexer {

	/**
	 * Input text data
	 */
	private char[] data;
	/**
	 * Last analyzed index
	 */
	private int currentIndex;
	/**
	 * Last found token
	 */
	private SmartScriptToken token;
	/**
	 * Current state
	 */
	private SmartScriptLexerState state;

	/**
	 * A constructor that takes in text to be tokenized and
	 * initializes lexer in its basic state
	 * 
	 * @param text Text to tokenize
	 * @throws IllegalArgumentException When dataset is null
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new SmartScriptLexerException("Input string can't be null");
		}

		this.data = text.toCharArray();
		this.state = SmartScriptLexerState.BASIC;
	}

	/**
	 * A method that looks for tokens in a given dataset and returns the
	 * first found token.
	 * 
	 * @return Token The following token
	 * @throws LexerException If input string is invalid or contains a number out of range 
	 * 							for a long type.
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("No tokens available");
		}


		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}

		if (state == SmartScriptLexerState.BASIC) {
			nextBasicToken();
		} else {
			skipWhitespace();
			nextInTagsToken();
		}

		return token;

	}
	
	
	/**
	 * A method that will find tokens contained within tags
	 * 
	 * @throws SmartScriptLexerException When tags are invalid or unbalanced 
	 */
	private void nextInTagsToken() {
		if (data[currentIndex] == '$') {
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '}') {
				changeState();
				return;
			} else {
				throw new SmartScriptLexerException("Invalid tags");
			}
		} else if (data[currentIndex] == '=' && token.getType() == SmartScriptTokenType.TAG) {
			token = new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString("="));
			currentIndex++;
		} else if (data[currentIndex] == '\"') {
			findString();
		} else if (data[currentIndex] == '@') {
			findFunction();
		} else if (Character.isLetter(data[currentIndex])) {
			findVar();
		} else if (Character.isDigit(data[currentIndex])) {
			findNumber();
		} else {
			findSymbol();
		}
	}
	
	/**
	 * A method that will find and tokenize supported symbols
	 * 
	 * @throws SmartScriptLexerException When an unsupported symbol is found
	 */
	private void findSymbol() {
		char current = data[currentIndex];

		if (current == '-' && currentIndex < data.length - 1 && Character.isDigit(data[currentIndex + 1])) {
			findNumber();
			return;
		} else if ((current == '+' || current == '-' || current == '*' || current == '/' || current == '^')
				&& (Character.isDigit(data[currentIndex + 1]) || Character.isWhitespace(data[currentIndex + 1]))) {
			token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, new ElementOperator(String.valueOf(current)));
			currentIndex++;
		} else {
			throw new SmartScriptLexerException("Unsupported symbol: " + data[currentIndex]);
		}
	}
	
	/**
	 * A method that will find and tokenize supported numbers. 
	 * It will create a token of type 'DOUBLE' or 'INTEGER' depending
	 * on what number is found 
	 * 
	 * @throws SmartScriptLexerException When the number format is invalid 
	 * 										or the number is out of range
	 */
	private void findNumber() {
		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] == '-') {
			sb.append(data[currentIndex++]);
		}

		if (!Character.isDigit(data[currentIndex])) {
			throw new SmartScriptLexerException("Invalid minus symbol placement");
		}
		
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			sb.append(data[currentIndex++]);
		}

		String number = sb.toString();

		try {
			if (number.contains(".")) {
				token = new SmartScriptToken(SmartScriptTokenType.DOUBLE,
						new ElementConstantDouble(Double.parseDouble(number)));
			} else {
				token = new SmartScriptToken(SmartScriptTokenType.INTEGER,
						new ElementConstantInteger(Integer.parseInt(number)));
			}
		} catch (NumberFormatException ex) {
			throw new SmartScriptLexerException("Invalid number format, was '" + number +"'.", ex);
		}

	}

	/**
	 * A method that will find variables with valid names and tokenize them
	 */
	private void findVar() {
		String name = checkName();
		token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, new ElementVariable(name));
	}

	/**
	 * A method that will find functions with valid names and tokenize them
	 */
	private void findFunction() {
		currentIndex++;
		String name = checkName();
		token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, new ElementFunction(name));
	}
	
	/**
	 * A method that will find valid string and tokenize them
	 */
	private void findString() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (isValidEscapeSequence()) {
//				sb.append("\\");
			}
			sb.append(data[currentIndex++]);
		}
		currentIndex++;
		token = new SmartScriptToken(SmartScriptTokenType.STRING, new ElementString(sb.toString()));
	}

	/**
	 * A method that will check if a variable/function name 
	 * is valid, and return it if it is. Throws an exception if
	 * it isn't
	 * 
	 * @return String containing a found, valid name
	 * @throws SmartScriptLexerException When name is invalid
	 */
	private String checkName() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;

		while (currentIndex < data.length && (Character.isLetter(data[currentIndex])
				|| Character.isDigit(data[currentIndex]) || data[currentIndex] == '_')) {
			if (first && !Character.isLetter(data[currentIndex])) {
				throw new SmartScriptLexerException("Name must start with a letter");
			}

			sb.append(data[currentIndex++]);
			first = false;
		}

		return sb.toString();
	}

	/**
	 * A method that will find the next token in basic state
	 * 
	 * @throws SmartScriptLexerException When it finds invalid tags
	 */
	private void nextBasicToken() {
		if (data[currentIndex] == '{') {
			if (currentIndex < data.length - 1 && data[currentIndex + 1] == '$') {
				changeState();
				return;
			} else {
				throw new SmartScriptLexerException("Invalid tags");
			}
		} else {
			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length && data[currentIndex] != '{') {
				if (isValidEscapeSequence()) {
//					sb.append("\\");
				}
				sb.append(data[currentIndex++]);
			}
			token = new SmartScriptToken(SmartScriptTokenType.TEXT, new ElementString(sb.toString()));
		}
	}

	/**
	 * A method that will check if the char at the current index is a valid 
	 * escape sequence for the current state.
	 * 
	 * @return Boolean true if the sequence is valid, false otherwise
	 * @throws SmartScriptLexerException if the sequence isn't valid
	 */
	private boolean isValidEscapeSequence() {
		if (data[currentIndex] == '\\') {
			if (currentIndex >= data.length - 1) {
				throw new SmartScriptLexerException("Invalid escape seqence");
			}

			currentIndex++;
			char nextChar = data[currentIndex];
			if (nextChar == '\\' || nextChar == 'n' || nextChar == 'r' || nextChar == 't'
					|| (nextChar == '{' && state == SmartScriptLexerState.BASIC)
					|| (nextChar == '"' && state == SmartScriptLexerState.IN_TAGS)) {
				
				if (nextChar == 'n') {
					data[currentIndex] = '\n';
				}
				
				if (nextChar == 'r') {
					data[currentIndex] = '\r';
				}
				
				if (nextChar == 't') {
					data[currentIndex] = '\t';
				}
				
				return true;
			} else {
				throw new SmartScriptLexerException("Invalid escape seqence");
			}
		}

		return false;
	}

	/**
	 * A method that will change the current state when called
	 */
	private void changeState() {
		if (state == SmartScriptLexerState.BASIC) {
			setState(SmartScriptLexerState.IN_TAGS);
		} else if (state == SmartScriptLexerState.IN_TAGS) {
			setState(SmartScriptLexerState.BASIC);
		}

		currentIndex += 2; //skip tags
		token = new SmartScriptToken(SmartScriptTokenType.TAG, new ElementString("TAG"));
	}

	/**
	 * A getter method for the last found token
	 * 
	 * @return Token Last found token
	 * @throws LexerException If no tokens have been found
	 */
	public SmartScriptToken getToken() {
		if (token == null) {
			throw new SmartScriptLexerException("No tokens found yet");
		}
		
		return token;
	}

	/**
	 * A helper method to skip all whitespace
	 */
	private void skipWhitespace() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (Character.isWhitespace(c)) {
				currentIndex++;
				continue;
			}
			break;
		}
	}
	
	/**
	 * A setter method for lexer state
	 * 
	 * @param state SmartScriptLexerState to set lexer in
	 * @throws IllegalArgumentException If desired state is null
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new SmartScriptLexerException("Lexer state can't be null");
		}

		this.state = state;
	}
}
