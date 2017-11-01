package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

import hr.fer.zemris.bf.utils.NodeVisitor;

/**
 * A node to represent binary operators
 * 
 * @author Franko Car
 *
 */
public class BinaryOperatorNode implements Node {

	/** Node name */
	private String name;
	/** Nodes that operator operates on */
	private List<Node> children;
	/** Operator function */
	private BinaryOperator<Boolean> operator;
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);		
	}

	/**
	 * A constructor
	 * 
	 * @param name Node name
	 * @param children List of children Nodes
	 * @param operator BinaryOperator
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		super();
		this.name = name;
		this.children = children;
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
	 * Children list getter
	 * 
	 * @return children List<Node>
	 */
	public List<Node> getChildren() {
		return children;
	}
	
	/**
	 * An operator getter
	 * 
	 * @return operator BinaryOperator<Boolean>
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}
	
	
}
