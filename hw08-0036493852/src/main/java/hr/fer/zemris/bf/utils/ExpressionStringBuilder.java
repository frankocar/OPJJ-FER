package hr.fer.zemris.bf.utils;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * A {@link NodeVisitor} that will build an expression string from a top level node
 * 
 * @author Franko Car
 *
 */
public class ExpressionStringBuilder implements NodeVisitor {

	/**
	 * List of words
	 */
	private List<String> strings;
	
	/**
	 * A default constructor
	 */
	public ExpressionStringBuilder() {
		super();
		strings = new LinkedList<>();
	}

	@Override
	public void visit(ConstantNode node) {
		strings.add(node.getValue() == true ? "TRUE" : "FALSE");
	}

	@Override
	public void visit(VariableNode node) {
		strings.add(node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		strings.add(node.getName().toUpperCase());		
		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		List<Node> children = node.getChildren();
		for (int i = 0, n = children.size(); i < n; i++) {
			children.get(i).accept(this);
			strings.add(node.getName().toUpperCase());
		}		
		
		if (strings.size() > 1) {
			strings.remove(strings.size() - 1);
		}

	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String x : strings) {
			sb.append(x);
			sb.append(" ");
		}
		return sb.toString();
	}
	

}
