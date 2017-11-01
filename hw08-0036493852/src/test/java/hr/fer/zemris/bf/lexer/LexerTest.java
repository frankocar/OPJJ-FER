package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTest {

	@Test(expected = LexerException.class)
	public void numericConstantTest() {
		Lexer lexer = new Lexer("0");
		Token token = lexer.nextToken();
		
		assertEquals(TokenType.CONSTANT, token.getTokenType());
		assertEquals(false, token.getTokenValue());
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getTokenType());
		
		token = lexer.nextToken();		
	}
	
	@Test
	public void booleanConstantTest() {
		TokenType[] expectedTypes = {TokenType.CONSTANT, TokenType.EOF};
		Object[] expectedValues = {true, null};
		
		Lexer lexer = new Lexer("tRue");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void simpleOperation1Test() {
		TokenType[] expectedTypes = {TokenType.OPERATOR, TokenType.VARIABLE, TokenType.EOF};
		Object[] expectedValues = {"not", "A", null};
		
		Lexer lexer = new Lexer("Not a");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void simpleOperation2Test() {
		TokenType[] expectedTypes = {TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.EOF};
		Object[] expectedValues = {"A", "and", "B", null};
		
		Lexer lexer = new Lexer("A aNd b");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void simpleOperation3Test() {
		TokenType[] expectedTypes = {TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.EOF};
		Object[] expectedValues = {"A", "or", "B", null};
		
		Lexer lexer = new Lexer("a or b");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void simpleOperation4Test() {
		TokenType[] expectedTypes = {TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.EOF};
		Object[] expectedValues = {"A", "xor", "B", null};
		
		Lexer lexer = new Lexer("a xoR b");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void operation1Test() {
		TokenType[] expectedTypes = { TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.VARIABLE, TokenType.EOF };
		Object[] expectedValues = {"A", "and", "B", "and", "C", null};
		
		Lexer lexer = new Lexer("A and b * c");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void operation2Test() {
		TokenType[] expectedTypes = { TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.VARIABLE, TokenType.EOF };
		Object[] expectedValues = {"A", "xor", "B", "xor", "C", null};
		
		Lexer lexer = new Lexer("a xor b :+: c");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void operation3Test() {
		TokenType[] expectedTypes = { TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE, TokenType.EOF };
		Object[] expectedValues = {"A", "or", "B", "xor", "C", "and", "D", null};
		
		Lexer lexer = new Lexer("a or b xor c and d");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void operationWithBracketsTest() {
		TokenType[] expectedTypes = { TokenType.OPEN_BRACKET, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.VARIABLE, TokenType.CLOSED_BRACKET, TokenType.OPERATOR, TokenType.OPERATOR,
				TokenType.OPEN_BRACKET, TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE,
				TokenType.CLOSED_BRACKET, TokenType.EOF };
		Object[] expectedValues = {'(', "D", "or", "B", ')', "xor", "not", '(',  "A", "or", "C", ')', null};
		
		Lexer lexer = new Lexer("(d or b) xor not (a or c)");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test
	public void variableTest() {
		TokenType[] expectedTypes = { TokenType.OPEN_BRACKET, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.VARIABLE, TokenType.CLOSED_BRACKET, TokenType.VARIABLE, TokenType.OPERATOR,
				TokenType.OPEN_BRACKET, TokenType.VARIABLE, TokenType.OPERATOR, TokenType.VARIABLE,
				TokenType.CLOSED_BRACKET, TokenType.EOF };
		Object[] expectedValues = {'(', "C", "or", "D", ')', "MOR", "not", '(',  "A", "or", "B", ')', null};
		
		Lexer lexer = new Lexer("(c or d) mor not (a or b)");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	@Test(expected = LexerException.class)
	public void invalidNumberTest() {
		TokenType[] expectedTypes = { TokenType.VARIABLE, TokenType.OPERATOR, TokenType.CONSTANT, TokenType.EOF};
		Object[] expectedValues = {"A", "and", 10, null};
		
		Lexer lexer = new Lexer("a and 10");
		
		universalTest(expectedTypes, expectedValues, lexer);		
	}
	
	private void universalTest(TokenType[] expectedTypes, Object[] expectedValues, Lexer lexer) throws LexerException{
		for (int i = 0; i < expectedTypes.length; i++) {
			Token token = lexer.nextToken();
			assertEquals(expectedTypes[i], token.getTokenType());
			assertEquals(expectedValues[i], token.getTokenValue());
		}		
	}

}
