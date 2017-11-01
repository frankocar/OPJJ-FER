package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * A node subclass to store for loops in
 * 
 * @author Franko Car
 *
 */
public class ForLoopNode extends Node {

	/**
	 * Variable to iterate over
	 */
	private final ElementVariable variable;
	/**
	 * Start index of iteration
	 */
	private final Element startExpression;
	/**
	 * End index of iteration
	 */
	private final Element endExpression;
	/**
	 * Step of iteration
	 */
	private final Element stepExpression;
	
	/**
	 * A constructor taking in all for loop data.
	 * None of the elements except of stepExpression can be null
	 * 
	 * @param variable Variable to iterate over
	 * @param startExpression Start index of iteration
	 * @param endExpression End index of iteration
	 * @param stepExpression Step of iteration
	 * @throws NullPointerException If any of the elements except of stepExpression are null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("variable, startExpression and endExpression can't be null");
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	
	/** 
	 * A getter for the variable
	 * 
	 * @return variable ElementVariable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/** 
	 * A getter for the startExpression
	 * 
	 * @return startExpression Element
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/** 
	 * A getter for the endExpression
	 * 
	 * @return endExpression Element
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/** 
	 * A getter for the stepExpression
	 * 
	 * @return stepExpression Element
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$FOR ");
		
		sb.append(variable.asText() + " ");
		sb.append(startExpression.asText() + " ");
		sb.append(endExpression.asText() + " ");		
		if (stepExpression != null) {
			sb.append(stepExpression.asText());
		}
		
		sb.append("$}");
		
		return sb.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
