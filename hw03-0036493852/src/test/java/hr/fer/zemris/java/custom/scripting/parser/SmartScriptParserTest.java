package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptParserTest {

	@Test
	public void simpleTest() {
		SmartScriptParser parser = new SmartScriptParser("This is text. {$= @function_1 \"This is a string\" $}");
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());
		
		assertEquals("This is text. ", ((TextNode) document.getChild(0)).getText());
		assertEquals("function_1", ((EchoNode) document.getChild(1)).getElements()[0].asText());
		assertEquals("This is a string", ((EchoNode) document.getChild(1)).getElements()[1].asText());
		
	}
	
	@Test
	public void unbalancedForLoopTest() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("This is text. {$FOR i 1 0$}");
			fail("Tags are unbalanced, an exception should have been thrown");
		} catch (SmartScriptParserException ex) {	
		}
	}
	
	@Test
	public void threeArgumentsForLoopTest() {
		SmartScriptParser parser = new SmartScriptParser("This is text. {$FOR i 3 4$} ForLoopText {$=forLoopVar$} @and {$END$}");
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());
		
		ForLoopNode loop = (ForLoopNode) document.getChild(1);
		assertEquals(3, loop.numberOfChildren());
		
		assertEquals(" ForLoopText ", ((TextNode) loop.getChild(0)).getText());
		assertEquals("forLoopVar", ((EchoNode) loop.getChild(1)).getElements()[0].asText());
		assertEquals(" @and ", ((TextNode) loop.getChild(2)).getText());
		
		assertEquals("i", loop.getVariable().asText());
		assertEquals("3", loop.getStartExpression().asText());
		assertEquals("4", loop.getEndExpression().asText());
	}
	
	@Test
	public void fourArgumentsForLoopTest() {
		SmartScriptParser parser = new SmartScriptParser("This is text. {$FOR i 3 4 5$} ForLoopText {$=forLoopVar$} @and {$END$}");
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());
		
		ForLoopNode loop = (ForLoopNode) document.getChild(1);
		
		assertEquals("i", loop.getVariable().asText());
		assertEquals("3", loop.getStartExpression().asText());
		assertEquals("4", loop.getEndExpression().asText());
		assertEquals("5", loop.getStepExpression().asText());
	}
	
	@Test
	public void exampleTest() {
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");
		
		DocumentNode document = parser.getDocumentNode();
		assertEquals(2, document.numberOfChildren());
		
		assertEquals("Example \\{$=1$}. Now actually write one ", ((TextNode) document.getChild(0)).getText());
		assertEquals("1", ((EchoNode) document.getChild(1)).getElements()[0].asText());		
	}

}
