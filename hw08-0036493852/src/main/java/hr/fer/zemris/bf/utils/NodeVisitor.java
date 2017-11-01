package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * An interface to describe a node visitor, a class that will visit each node and
 * perform an operation on it
 * 
 * @author Franko Car
 *
 */
public interface NodeVisitor {

	/**
	 * A method to visit a {@link ConstantNode}
	 * 
	 * @param node ConstantNode
	 */
	void visit(ConstantNode node);
	
	/**
	 * A method to visit a {@link VariableNode}
	 * 
	 * @param node VariableNode
	 */
	void visit(VariableNode node);
	
	/**
	 * A method to visit a {@link UnaryOperatorNode}
	 * 
	 * @param node UnaryOperatorNode
	 */
	void visit(UnaryOperatorNode node);
	
	/**
	 * A method to visit a {@link BinaryOperatorNode}
	 * 
	 * @param node BinaryOperatorNode
	 */
	void visit(BinaryOperatorNode node);
	
}
