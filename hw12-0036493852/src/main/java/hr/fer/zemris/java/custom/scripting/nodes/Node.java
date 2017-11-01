package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * A top level node class storing its children.
 * Shouldn't really be used itself, it's better to use
 * its appropriate subclasses
 * 
 * @author Franko Car
 *
 */
public abstract class Node {

	/**
	 * A list of children
	 */
	private List<Node> children;
	
	/**
	 * A method to add a child to the node
	 * 
	 * @param child Node to be added
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new IllegalArgumentException("Node can't be null");
		}
		
		if (children == null) {
			children = new ArrayList<>();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns the number of children of a node
	 * 
	 * @return numberOfChildren int number
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		
		return children.size();
	}
	
	/**
	 * Gets the child at a given index
	 * 
	 * @param index int index
	 * @return child Node child
	 */
	public Node getChild(int index) {
		try {
			return (Node) children.get(index);
		} catch (IndexOutOfBoundsException | NullPointerException ex) {
			throw new IllegalArgumentException("Requested node doesn't exist", ex);
		}		
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	/**
	 * Accept a visit from a given visitor
	 * 
	 * @param visitor Visitor
	 */
	public abstract void accept(INodeVisitor visitor);
}
