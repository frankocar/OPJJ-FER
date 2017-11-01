package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * A node to represent unary operators
 * 
 * @author Franko Car
 *
 */
public class UnaryOperatorNode implements Node {

	/** Node name */
	private String name;
	/** Node that operator operates on */
	private Node child;
	/** Operator function */
	private UnaryOperator<Boolean> operator;
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);		
	}

	/**
	 * A constructor
	 * 
	 * @param name Node name
	 * @param child Child Nodes
	 * @param operator BinaryOperator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.child = child;
		this.operator = operator;
	}
	
	/**
	 * A name getter
	 * 
	 * @return name String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Child node getter
	 * 
	 * @return child Node
	 */
	public Node getChild() {
		return child;
	}
	
	/**
	 * An operator getter
	 * 
	 * @return operator UnaryOperator<Boolean>
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}
		
}
