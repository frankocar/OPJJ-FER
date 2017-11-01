package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A Node-type class describing a top level node of a document.
 * Doesn't contain any data itself, just keeps track of its
 * children.
 * 
 * @author Franko Car
 *
 */
public class DocumentNode extends Node {
	//Doesn't really do anything, here just to make it clear that a node is a document
	
	/**
	 * A constructor, does nothing
	 */
	public DocumentNode() {}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
	@Override
	public String toString() {
		return "";
	}
}
