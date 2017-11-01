package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * A parser for SmartScript. It will read the input file using
 * SmartScriptLexer and parse the tokens as nodes to build the 
 * file structure. It will start with a document node, which 
 * can be accessed by getDocument() method.
 * 
 * It throws SmartScriptParserException when something is wrong,
 * such as unbalanced tags or unclosed for loop.
 * 
 * @author Franko Car
 *
 */
public class SmartScriptParser {
	
	/**
	 * A top level document to add children to
	 */
	private DocumentNode documentNode;
	/**
	 * A lexer to tokenize the data
	 */
	private SmartScriptLexer lexer;
	/**
	 * A stack to deal with nonempty tags
	 */
	ObjectStack stack;
	
	/**
	 * A constructor that will take in the data
	 * string and start parsing it.
	 * 
	 * @param data
	 */
	public SmartScriptParser(String data) {
		if (data == null) {
			throw new SmartScriptParserException("Input data can't be null");
		}
		
		lexer = new SmartScriptLexer(data);
		
		startParse();
	}
	
	/**
	 * A method that starts the parsing process
	 * @throws SmartScriptParserException when it encounters a problem with the tags
	 */
	private void startParse() {
		stack = new ObjectStack();		
		documentNode = new DocumentNode();		
		stack.push(documentNode);
		
		SmartScriptToken current = null;
		try {
			current = lexer.nextToken();
		} catch (SmartScriptLexerException e) {
			throw new  SmartScriptParserException(e);
		}
		
		while (current != null && current.getType() != SmartScriptTokenType.EOF) {
			if (current.getType() == SmartScriptTokenType.TAG) {
				
				try {
					current = lexer.nextToken();
				} catch (SmartScriptLexerException e) {
					throw new  SmartScriptParserException(e);
				}
				
				if (!(current.getType() == SmartScriptTokenType.VARIABLE || current.getValue().asText().equals("="))) {
					throw new SmartScriptParserException("Invalid tags");
				}
				
				String tag = current.getValue().asText().toLowerCase();
				
				if (tag.equals("for")) {
					forTag();
				} else if (tag.equals("end")) {
					endTag();
				} else if (tag.equals("=")) {
					equalsTag();
				} else {
					throw new SmartScriptParserException("Unsupported tags");
				}
				
			} else {
				Node document = (Node) stack.peek();
				document.addChildNode(new TextNode(current.getValue().asText()));
			}
			
			try {
				current = lexer.nextToken();
			} catch (SmartScriptLexerException e) {
				throw new  SmartScriptParserException(e);
			}
		}
		
		if (stack.size() != 1) {
			throw new SmartScriptParserException("Unbalanced tags");
		}
		
	}
	
	
	/**
	 * A private method to help parse for tags.
	 * Will make sure that all data is present,
	 * there is an ending tag and the name is valid.
	 * Throws an exception if any of those aren't true.
	 * 
	 * @throws SmartScriptParserException If tags are unbalanced, 
	 * 			order is invalid or there is a wrong number of arguments
	 */
	private void forTag() {
		SmartScriptToken token = null;
		try {
			token = lexer.nextToken();
		} catch (SmartScriptLexerException e) {
			throw new  SmartScriptParserException(e);
		}
		ObjectStack helperStack = new ObjectStack();
		
		boolean first = true;
		while (token != null && token.getType() != SmartScriptTokenType.TAG) {
			if (token.getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("For tag not closed before the end");
			}
			
			if (token.getType() == SmartScriptTokenType.STRING 
					|| token.getType() == SmartScriptTokenType.DOUBLE
					|| token.getType() == SmartScriptTokenType.INTEGER
					|| token.getType() == SmartScriptTokenType.VARIABLE
					) {
				if (first && token.getType() != SmartScriptTokenType.VARIABLE) {
					throw new SmartScriptParserException("For tag doesn't begin with a name");
				}
				
				helperStack.push(token);
			}
			
			try {
				token = lexer.nextToken();
			} catch (SmartScriptLexerException e) {
				throw new  SmartScriptParserException(e);
			}
			first = false;
			
		}
		
		if (helperStack.size() < 3 || helperStack.size() > 4) {
			throw new SmartScriptParserException("Invalid number of arguments in a for tag");
		}
		
		Element step = null;
		SmartScriptToken temp = null;
		if (helperStack.size() == 4) {
			temp = (SmartScriptToken) helperStack.pop();
			step = temp.getValue();
		}		
		temp = (SmartScriptToken) helperStack.pop();
		Element end = temp.getValue();
		
		temp = (SmartScriptToken) helperStack.pop();
		Element start = temp.getValue();
		
		temp = (SmartScriptToken) helperStack.pop();
		ElementVariable var = (ElementVariable) temp.getValue();
		
		ForLoopNode newNode = new ForLoopNode(var, start, end, step);

		
		Node parent = (Node) stack.peek();
		parent.addChildNode(newNode);
		stack.push(newNode);
		
	}
	
	/**
	 * A private method to help parse end tags.
	 * Will throw an exception when tags are unbalanced or invalid
	 * 
	 * @throws SmartScriptParserException When tags are unbalanced or invalid.
	 */
	private void endTag() {
		try {
			stack.pop(); //remove ending braces from previous method
		} catch(EmptyStackException ex) {
			throw new SmartScriptParserException("Unbalanced tags", ex);
		}
		
		if (stack.size() == 0) {
			throw new SmartScriptParserException("Unbalanced tags");
		}
		
		SmartScriptToken token = null;
		try {
			token = lexer.nextToken();
		} catch (SmartScriptLexerException e) {
			throw new  SmartScriptParserException(e);
		}
		if (token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptParserException("Tag not closed");
		} else if (token.getType() != SmartScriptTokenType.TAG) {
			throw new SmartScriptParserException("Invalid ending tag");
		}		
	}
	
	/**
	 * A private method to help parse echo tags.
	 * Will throw an exception when tags are invalid 
	 * or text(not string) is found
	 * 
	 * @throws SmartScriptParserException When tags are invalid text is found.
	 */
	private void equalsTag() {
		
		SmartScriptToken token = null;
		try {
			token = lexer.nextToken();
		} catch (SmartScriptLexerException e) {
			throw new  SmartScriptParserException(e);
		}
		ObjectStack helperStack = new ObjectStack();
		
		while (token != null && token.getType() != SmartScriptTokenType.TAG) {
			if (token.getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("Tag not closed");
			}
			
			if (token.getType() == SmartScriptTokenType.TEXT) {
				throw new SmartScriptParserException("Text found in tags");
			}
			
			helperStack.push(token.getValue());
			try {
				token = lexer.nextToken();
			} catch (SmartScriptLexerException e) {
				throw new  SmartScriptParserException(e);
			}
		}
		
		Element[] args = new Element[helperStack.size()];
		
		int i = 0;
		while (!helperStack.isEmpty()) {
			args[args.length - (1 + i)] = (Element) helperStack.pop();
			i++;
		}
		
		EchoNode newNode = new EchoNode(args);
		Node parent = (Node) stack.peek();
		parent.addChildNode(newNode);
		
	}
	
	/**
	 * A getter to get top level tree node
	 * of a parsed input
	 * 
	 * @return parent DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
}
