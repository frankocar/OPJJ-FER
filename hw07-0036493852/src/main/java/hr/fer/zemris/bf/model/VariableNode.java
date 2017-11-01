package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * A node to represent variables
 * 
 * @author Franko Car
 *
 */
public class VariableNode implements Node {

	/** Node name */
	private String name;
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);		
	}

	/**
	 * A constructor
	 * 
	 * @param name Node name
	 */
	public VariableNode(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * A value getter
	 * 
	 * @return value boolean
	 */
	public String getName() {
		return name;
	}
	
}
