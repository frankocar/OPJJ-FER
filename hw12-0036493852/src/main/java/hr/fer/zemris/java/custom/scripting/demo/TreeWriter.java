package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * A class to traverse a node tree using a visitor
 * 
 * @author Franko Car
 *
 */
public class TreeWriter {

	/**
	 * Main method
	 * 
	 * @param args Path of a document to parse
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
		} 
		DocumentNode document = parser.getDocumentNode();
		WriterVisitor visitor = new WriterVisitor();
		document.accept(visitor);
		
	}
	
	/**
	 * Visitor to use
	 * 
	 * @author Franko Car
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			visitChildren(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			visitChildren(node);
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			visitChildren(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}
		
		/**
		 * Visits all children
		 * 
		 * @param node Node
		 */
		private void visitChildren(Node node) {
			System.out.print(node);
			
			int size = node.numberOfChildren();
			for (int i = 0; i < size; i++) {
				node.getChild(i).accept(WriterVisitor.this);
			}
		}
		
	}
	
}
