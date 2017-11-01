package hr.fer.zemris.bf.model;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * A node to represent constants
 * 
 * @author Franko Car
 *
 */
public class ConstantNode implements Node {
	
	/** Node value */
	private boolean value;
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);		
	}

	/**
	 * A constructor
	 * 
	 * @param value Node value
	 */
	public ConstantNode(boolean value) {
		super();
		this.value = value;
	}

	/**
	 * A value getter
	 * 
	 * @return value boolean
	 */
	public boolean getValue() {
		return value;
	}

}
