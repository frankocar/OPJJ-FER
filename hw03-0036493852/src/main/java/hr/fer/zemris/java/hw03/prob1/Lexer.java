package hr.fer.zemris.java.hw03.prob1;

/**
 * A class that will take in input text and tokenize it.
 * Every {@code nextToken()} call will generate a new token until EOF is found.
 * {@code getToken()} call will return the last found token.
 * A Lexer can have two states as defined by LexerType enum, basic and extended.
 * 
 * In basic state, lexer will separate words, numbers and symbols. It will support '\'
 * as an escape char that will make the following digit to be recognized as a letter
 * in a word. Every number will be recognized as a number, but it won't be supported 
 * if it's out of range for long type. Every other char will be recognized as a symbol.
 * 
 * In extended state it will treat everything as a word and an escape char won't be
 * supported. When '#' is found, it will return it as a symbol to be used to 
 * switch states. Words will be split by whitespace.
 * 
 * LexerException will be thrown if there is an error in the given dataset, and 
 * IllegalArgumentException will be thrown when the initial parameters aren't supported.
 * 
 * @author Franko Car
 *
 */
public class Lexer {

	/**
	 * Input text data
	 */
	private char[] data;
	/**
	 * Last found token
	 */
	private Token token;
	/**
	 * Last analyzed index
	 */
	private int currentIndex;
	/**
	 * Current state
	 */
	private LexerState state;
	
	/**
	 * A constructor that takes in text to be tokenized and
	 * initializes lexer in its basic state
	 * 
	 * @param text Text to tokenize
	 * @throws IllegalArgumentException When dataset is null
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input string can't be null");
		}
		
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
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
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available");
		}
		
		skipWhitespace();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (findWord()) return token;		
		if (findSymbol()) return token;	
		if (findNumber()) return token;		
		
		throw new LexerException("Input string invalid, no tokens found");
	}
	
	/**
	 * A helper method to check if the next string is a word. If it is, it
	 * generates a token and returns true
	 * 
	 * @return Boolean True if a word is found and generated
	 * @throws LexerException if a word contains an unnecessary escape char or is empty
	 */
	private boolean findWord() {
		
		String value = null;
		
		if (state == LexerState.BASIC && (Character.isLetter(data[currentIndex]) || isEscape(0))) {
			value = findBasicWord();
		} else if (state == LexerState.EXTENDED && Character.isLetterOrDigit(data[currentIndex])) {
			value = findExtendedWord();
		}
		
		if (value == null) {
			return false;
		}
		
		if (value.isEmpty()) {
			throw new LexerException("A word can't be empty");
		}
		
		token = new Token(TokenType.WORD, value);
		return true;
		
	}
	
	/**
	 * A method that will read the next word in the extended state.
	 * 
	 * @return String that was read
	 */
	private String findExtendedWord() {
		int startIndex = currentIndex++;
		
		while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
			if (data[currentIndex] == '#') {
				changeState();
				break;
			}
			
			currentIndex++;
		}		
		
		int endIndex = currentIndex;
		
		return new String(data, startIndex, endIndex - startIndex);
	}
	
	/**
	 * A method that will read the next word in the basic state.
	 * 
	 * @return String that was read
	 */
	private String findBasicWord() {
		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] != '\\') { // add current char if it isn't '\', skip if it is
			sb.append(data[currentIndex]);
		}

		currentIndex++;
		
		while (currentIndex < data.length
				&& (Character.isLetter(data[currentIndex]) || isEscape(-1) || isEscape(0))) {		
			
			if (data[currentIndex] == '#') {
				changeState();
				break;
			}
			
			if (Character.isLetter(data[currentIndex]) && isEscape(-1)) {
				throw new LexerException("Unnecessary escape char");
			}

			if (isEscape(0) && !isEscape(-1)) { // if current char is unescaped '\', skip it
				currentIndex++;
				continue;
			}

			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return sb.toString().trim();
	}
	
	/**
	 * A method that will switch current state when called
	 */
	private void changeState() {
		if (state == LexerState.BASIC) {
			setState(LexerState.EXTENDED);
		} else {
			setState(LexerState.BASIC);
		}
		
	}

	/**
	 * A helper method to check if the next string is a number. If it is, it
	 * generates a token and returns true
	 * 
	 * @return Boolean True if a number is found and generated
	 * @throws LexerException if a number found is not a valid long type
	 */
	private boolean findNumber() {
		if (Character.isDigit(data[currentIndex])) {
			int startIndex = currentIndex++;
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}			
			int endIndex = currentIndex;	
			
			long value;
			try {
				value = Long.parseLong(new String(data, startIndex, endIndex - startIndex));
				token = new Token(TokenType.NUMBER, value);
				return true;
			} catch (NumberFormatException ex) {
				throw new LexerException("Input is invalid, number is out of range", ex);
			}		
		}
		return false;
	}
	
	/**
	 * A helper method to check if the next string is a symbol. If it is, it
	 * generates a token and returns true
	 * 
	 * @return Boolean True if a symbol is found and generated
	 */
	private boolean findSymbol() {
		if (!Character.isLetterOrDigit(data[currentIndex])) {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return true;
		}
		return false;
	}
	
	/**
	 * A helper method to check whether a current symbol + offset is an escape char
	 * 
	 * @param offset Offset from current char
	 * @return Boolean value if it is an escape char
	 */
	private boolean isEscape(int offset) {
		if (data[currentIndex + offset] == '\\') {
			return true;
		}
		return false;
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
	 * A getter method for the last found token
	 * 
	 * @return Token Last found token
	 * @throws LexerException If no tokens have been found
	 */
	public Token getToken() {
		if (token == null) {
			throw new LexerException("No tokens found yet");
		}
		
		return token;
	}
	
	/**
	 * A setter method for lexer state
	 * 
	 * @param state LexerState State to set lexer in
	 * @throws IllegalArgumentException If desired state is null
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Lexer state can't be null");
		}
		
		this.state = state;
	}
	
}
