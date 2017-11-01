package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * An interface defining a node visitor
 * 
 * @author Franko Car
 *
 */
public interface INodeVisitor {

	/**
	 * Visit TextNode
	 * 
	 * @param node TextNode
	 */
	void visitTextNode(TextNode node);
	/**
	 * Visit ForLoopNode
	 * 
	 * @param node ForLoopNode
	 */
	void visitForLoopNode(ForLoopNode node);
	/**
	 * Visit EchoNode
	 * 
	 * @param node EchoNode
	 */
	void visitEchoNode(EchoNode node);
	/**
	 * Visit DocumentNode
	 * 
	 * @param node DocumentNode
	 */
	void visitDocumentNode(DocumentNode node);
	
	
	
}
