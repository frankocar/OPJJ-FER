package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * A {@link NodeVisitor} that will print an expression as a tree
 * 
 * @author Franko Car
 *
 */
public class ExpressionTreePrinter implements NodeVisitor {

	/** Current indentation level */
	private int level;
	
	/**
	 * A default constructor
	 */
	public ExpressionTreePrinter() {
		super();
		this.level = 0;
	}

	@Override
	public void visit(ConstantNode node) {
		System.out.println(getSpace() + (node.getValue() == true ? "1" : "0"));

	}

	@Override
	public void visit(VariableNode node) {
		System.out.println(getSpace() + node.getName());
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		System.out.println(getSpace() + node.getName());
		
		level++;
		node.getChild().accept(this);
		level--;
		

	}

	@Override
	public void visit(BinaryOperatorNode node) {
		System.out.println(getSpace() + node.getName());
		
		level++;
		for (Node child : node.getChildren()) {
			child.accept(this);
		}
		level--;

	}
	
	/**
	 * A method that returns whitespace according to the current level of indentation
	 * 
	 * @return string Whitespace of necessary length
	 */
	private String getSpace() {
		return level == 0 ? "" : String.format("%" + level * 2 + "s", "");
	}

}
