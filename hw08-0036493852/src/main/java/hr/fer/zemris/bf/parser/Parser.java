package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * A class to parse a binary expression. It uses tokens produced by {@link Lexer}
 * and parses their content according to given grammar:
 * 
 *		S  -> E1
 *		E1 -> E2 (OR E2)*
 *		E2 -> E3 (XOR E3)*
 *		E3 -> E4 (AND E4)*
 *		E4 -> NOT E4 | E5
 *		E5 -> VAR	| KONST		| '(' E1 ')'
 *
 *
 * 
 * @author Franko Car
 *
 */
public class Parser {

	/** Lexer to use */
	private Lexer lexer;
	/** Top-level node */
	private Node node;
	/** Last generated token */
	private Token token;
	
	/**
	 * A constructor
	 * 
	 * @param expression String to parse
	 * @throws ParserException if an unexpected token comes up
	 */
	public Parser(String expression) {
		if (expression == null) {
			throw new ParserException("Input data can't be null");
		}
		
		lexer = new Lexer(expression);
		
		try {
			node = build();			
		} catch (LexerException ex) {
			throw new ParserException("Lexer has thrown exception: " + ex.getMessage(), ex);
		}
	}
	
	
	/**
	 * A method to start expression building process.
	 * 
	 * @return node Top-level expression node
	 */
	private Node build() {
		token = lexer.nextToken();
		return parseOr();
	}

	/**
	 * A method to parse and operators or its values
	 * 
	 * @return node Top-level or node
	 */
	private Node parseOr() {
		Node first = parseXor();
		
		isTokenValid();		
		List<Node> list = new ArrayList<Node>();
		list.add(first);
		
		while (isTokenOfType(TokenType.OPERATOR) && token.getTokenValue().equals("or")) {
			token = lexer.nextToken();
			Node second = parseXor();
			list.add(second);			
		}
		
		if (list.size() > 1) {
			first = new BinaryOperatorNode("or", list, (x, y) -> x || y);
		}
		
		return first;
	}
	
	/**
	 * A method to parse xor operators and its values
	 * 
	 * @return node Top-level xor node
	 */
	private Node parseXor() {
		Node first = parseAnd();
		
		isTokenValid();		
		List<Node> list = new ArrayList<Node>();
		list.add(first);		
		
		while (isTokenOfType(TokenType.OPERATOR) && token.getTokenValue().equals("xor")) {
			token = lexer.nextToken();
			Node second = parseAnd();
			list.add(second);			
		}
		
		if (list.size() > 1) {
			first = new BinaryOperatorNode("xor", list, (x, y) -> x ^ y);
		}
		
		return first;
	}
	
	/**
	 * A method to parse and operators and its values
	 * 
	 * @return node Top-level and node
	 */
	private Node parseAnd() {
		Node first = parseAtomic();
		
		isTokenValid();		
		List<Node> list = new ArrayList<Node>();
		list.add(first);		
		
		while (isTokenOfType(TokenType.OPERATOR) && token.getTokenValue().equals("and")) {
			token = lexer.nextToken();
			Node second = parseAtomic();
			list.add(second);			
		}
		
		if (list.size() > 1) {
			first = new BinaryOperatorNode("and", list, (x, y) -> x && y);
		}
		
		return first;
	}

	/**
	 * A method to parse atomic values and "not" operators.
	 * 
	 * @return node Top-level operation node
	 * @throws ParserException when an unexpected token is found
	 */
	private Node parseAtomic() {
		
		if (isTokenOfType(TokenType.OPERATOR) && token.getTokenValue().equals("not")) {
			token = lexer.nextToken();
			Node negated = parseAtomic();
			return new UnaryOperatorNode("not", negated, x -> !x);
		}
		
		if (isTokenOfType(TokenType.CONSTANT)) {
			Node node = new ConstantNode((boolean) token.getTokenValue());
			token = lexer.nextToken();
			return node;
		}
		
		if (isTokenOfType(TokenType.VARIABLE)) {
			Node node = new VariableNode((String) token.getTokenValue());
			token = lexer.nextToken();
			return node;
		}
		
		if (isTokenOfType(TokenType.OPEN_BRACKET)) {
			token = lexer.nextToken();
			node = parseOr();
			
			if(isTokenOfType(TokenType.EOF)) {
				throw new ParserException("Expected ')' but found EOF.");
			}
			
			if(!isTokenOfType(TokenType.CLOSED_BRACKET)) {
				throw new ParserException("Closing brackets were expected.");
			}
			
			token = lexer.nextToken();
			return node;
		}
		
		throw new ParserException("Unexpected token found: {" + token.toString() + "}.");
		
	}
	
	/**
	 * A method to check validity of a token, throws an exception if invalid
	 * 
	 * @throws ParserException if token is invalid
	 */
	private void isTokenValid() throws ParserException {
		if ((!isTokenOfType(TokenType.OPERATOR)
				|| (isTokenOfType(TokenType.OPERATOR) && token.getTokenValue().equals("not")))
				&& !isTokenOfType(TokenType.EOF) && !isTokenOfType(TokenType.CLOSED_BRACKET)) {
			throw new ParserException("Unexpected token: " + token.toString());
		}
	}

	/**
	 * A getter for top-level node
	 * 
	 * @return node Top level node
	 */
	public Node getExpression() {
		return node;
	}
	
	/**
	 * A simple method to compare the type of the last generated with a given type
	 * 
	 * @param type TokenType to check against
	 * @return true if same, false if different
	 */
	private boolean isTokenOfType(TokenType type) {
		if (token == null) {
			return false;
		}
		return token.getTokenType() == type;
	}
	
}
