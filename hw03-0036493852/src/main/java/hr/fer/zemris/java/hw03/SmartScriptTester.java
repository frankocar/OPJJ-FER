package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * A program to test SmartScript lexer and parser.
 * Takes in a file containing a script via command
 * line argument, parses it and then prints it out
 * as close to the original as possible(ignores
 * whitespace in tags)
 * 
 * 
 * @author Franko Car
 *
 */
public class SmartScriptTester {

	/**
	 * A main method that starts the program
	 * 
	 * @param args Program arguments
	 * @throws IllegalArgumentException When wrong number of arguments are given
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			throw new IllegalArgumentException("There should be exactly one argument");
		}
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.exit(1);
		}
		
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			e.printStackTrace();
			System.out.println(e);
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like
													// original content of
													// docBody
	}
	
	/**
	 * A method to recreate original document from a parsed structure
	 * 
	 * @param document Top-level document node
	 * @return String of a recreated document
	 */
	private static String createOriginalDocumentBody(Node document) { 
		if (document == null) {
			throw new IllegalArgumentException("Document can't be null");
		}
		
		StringBuilder sb = new StringBuilder();
			
		for (int i = 0, childrenNumber = document.numberOfChildren(); i < childrenNumber; i++) {
			Node temp = document.getChild(i);
			
			if (temp instanceof EchoNode) {
				Element[] echoElements = ((EchoNode) temp).getElements();
				sb.append("{$=");
				
				for(int j = 0; j < echoElements.length; j++) {
					sb.append(formatElement(j, echoElements));
					sb.append(" ");
				}
				
				sb.append("$}");
			} else if (temp instanceof ForLoopNode) {
				sb.append(
						String.format(
								"{$ FOR %s %s %s%s $}",
								((ForLoopNode) temp).getVariable().asText(),
								((ForLoopNode) temp).getStartExpression().asText(),
								((ForLoopNode) temp).getEndExpression().asText(),
								((ForLoopNode) temp).getStepExpression() == null ? 
										"" : " " + ((ForLoopNode) temp).getStartExpression().asText()
								)
						);
				
				sb.append(createOriginalDocumentBody(temp));
				sb.append("{$ END $}");				
			} else if (temp instanceof TextNode) {
				sb.append(((TextNode) temp).getText());
			}			
		}
		
		return sb.toString();
	}
	
	/**
	 * A method to format the output of an element to contain
	 * original markers
	 * 
	 * @param index Index of an element in an array
	 * @param elements Array of elements
	 * @return String formatted element
	 */
	private static String formatElement(int index, Element[] elements) {
		if (elements[index] instanceof ElementString) {
			return String.format("\"%s\"", elements[index].asText());
		} else if (elements[index] instanceof ElementFunction) {
			return String.format("@%s", elements[index].asText());
		} else {
			return elements[index].asText();
		}
	}
	

}
