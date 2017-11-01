package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * Interface to describe Node objects
 * 
 * @author Franko Car
 *
 */
public interface Node {

	/**
	 * A method to accept a NodeVisitor for each node
	 * 
	 * @param visitor NodeVisitor
	 */
	void accept(NodeVisitor visitor);
	
}
