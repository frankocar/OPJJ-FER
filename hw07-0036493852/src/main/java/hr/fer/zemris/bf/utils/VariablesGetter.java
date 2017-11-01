package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * A {@link NodeVisitor} that will return a find a sorted list of uniqe
 * variables in a given expression
 * 
 * @author Franko Car
 *
 */
public class VariablesGetter implements NodeVisitor {

	/** A current set of variables */
	private Set<String> variables;	
	
	/**
	 * A default constructor
	 */
	public VariablesGetter() {
		variables = new TreeSet<>();
	}

	@Override
	public void visit(ConstantNode node) {
		return;

	}

	@Override
	public void visit(VariableNode node) {
		variables.add(node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
	}
	
	/**
	 * A getter for the list of variables
	 * 
	 * @return List of strings representing variable names
	 */
	public List<String> getVariables() {
		return new ArrayList<String>(variables);
	}

	
	
}
