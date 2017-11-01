package hr.fer.zemris.bf.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * A {@link NodeVisitor} that will evaluate an expression given as a top-level node
 * 
 * @author Franko Car
 *
 */
public class ExpressionEvaluator implements NodeVisitor {

	/** Current values */
	private boolean[] values;
	/** Map that maps variables to positions */
	private Map<String, Integer> positions;
	/** Stack to use */
	private Stack<Boolean> stack = new Stack<>();
	
	/**
	 * A constructor 
	 * 
	 * @param variables List of variable names
	 */
	public ExpressionEvaluator(List<String> variables) {
		positions = new HashMap<>();
		
		for (int i = 0, n = variables.size(); i < n; i++) {
			positions.put(variables.get(i), i);
		}
	}
	
	/**
	 * A method to set starting values and reset the process.
	 * 
	 * @param values boolean array of values
	 */
	public void setValues(boolean[] values) {
		this.values = Arrays.copyOf(values, values.length);
		start();
	}
	
	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());

	}

	@Override
	public void visit(VariableNode node) {
		if (positions.get(node.getName()) == null) {
			throw new IllegalStateException("Unknown variable");
		}
		
		stack.push(values[positions.get(node.getName())]);

	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);

		stack.push(node.getOperator().apply(stack.pop()));
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		for (Node child : node.getChildren()) {
			child.accept(this);
		}

		stack.push(node.getOperator().apply(stack.pop(), stack.pop()));
	}
	
	/**
	 * A method to restart the process
	 */
	public void start() {
		stack = new Stack<>();		
	}
	
	/**
	 * Returns the evaluation result
	 * 
	 * @return result boolean
	 * @throws IllegalStateException If a stack size isn't equal to 1, error happened
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Stack size mismatch");
		}
		
		return stack.peek();
	}

}
