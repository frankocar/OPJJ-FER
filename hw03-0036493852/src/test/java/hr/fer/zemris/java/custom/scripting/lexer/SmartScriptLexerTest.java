package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;

public class SmartScriptLexerTest {

	@Test
	public void nextTokenTest() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is text. {$= \"This is a string\" @function_1 $}");
		
		SmartScriptTokenType[] expectedTypes = new SmartScriptTokenType[] {
				SmartScriptTokenType.TEXT, 			//	"This is text. "
				SmartScriptTokenType.TAG, 			//	"TAG"
				SmartScriptTokenType.STRING,		//	"="
				SmartScriptTokenType.STRING,		//	"This is a string"
				SmartScriptTokenType.FUNCTION,		//	"function_1"
				SmartScriptTokenType.TAG			//	"TAG"
				};
		
		String[] expectedValues = new String[] {"This is text. ", "TAG", "=", "This is a string", "function_1", "TAG"};
		
		SmartScriptToken token;			
		for (int i = 0; i < expectedTypes.length; i++) {
			token = lexer.nextToken();
			assertEquals(expectedTypes[i], token.getType());
			assertEquals(expectedValues[i], token.getValue().asText());
		}
		
		token = lexer.nextToken();
		assertEquals(SmartScriptTokenType.EOF, token.getType());
	}
	
	@Test
	public void getTokenTest() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is text. {$= \"This is a string\" @function_1 $}");
		
		SmartScriptToken token = lexer.nextToken();
		while (token.getType() != SmartScriptTokenType.EOF) {
			assertEquals(token, lexer.getToken());
			token = lexer.nextToken();
		}
	}
	
	

}
