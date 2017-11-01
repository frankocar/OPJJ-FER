package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An engine to allow SmartScript scripts execution
 * 
 * @author Franko Car
 *
 */
public class SmartScriptEngine {
	
	/**
	 * Top-level document node
	 */
	private DocumentNode documentNode;
	/**
	 * Context of a request
	 */
	private RequestContext requestContext;
	/**
	 * Multistack used to keep track of operations
	 */
	private ObjectMultistack multistack;
	
	/**
	 * A map of all available functions
	 */
	private Map<String, Consumer<Deque<Object>>> functions;
	
	/**
	 * Initializes a map of all functions
	 */
	private void initFunctions() {
		functions = new HashMap<>();

		functions.put("sin", (x) -> {
			double value;
			try {
				value = Double.parseDouble(x.pop().toString());
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid argument type");
			}
			x.push(Math.sin(Math.toRadians(value)));
		});

		functions.put("decfmt", (x) -> {
			DecimalFormat format;
			double value;
			try {
				format = new DecimalFormat(x.pop().toString());
				value = Double.parseDouble(x.pop().toString());
			} catch (IllegalArgumentException | NullPointerException e) {
				throw new IllegalArgumentException("Invalid argument type");
			}
			x.push(format.format(value));
		});

		functions.put("dup", (x) -> {
			x.push(x.peek());
		});

		functions.put("swap", (x) -> {
			Object a = x.pop();
			Object b = x.pop();
			x.push(a);
			x.push(b);
		});

		functions.put("setMimeType", (x) -> {
			requestContext.setMimeType(x.pop().toString());
		});

		functions.put("paramGet", (x) -> {
			Object dv = x.pop();
			String name = x.pop().toString();

			Object value = requestContext.getParameter(name);
			if (value == null) {
				value = dv;
			}

			x.push(value);
		});

		functions.put("pparamGet", (x) -> {
			Object dv = x.pop();
			String name = x.pop().toString();

			Object value = requestContext.getPersistentParameter(name);
			if (value == null) {
				value = dv;
			}

			x.push(value);
		});

		functions.put("pparamSet", (x) -> {
			String name = x.pop().toString();
			String value = x.pop().toString();

			requestContext.setPersistentParameter(name, value);
		});

		functions.put("pparamDel", (x) -> {
			String name = x.pop().toString();

			requestContext.removePersistentParameter(name);
		});

		functions.put("tparamGet", (x) -> {
			Object dv = x.pop();
			String name = x.pop().toString();

			Object value = requestContext.getTemporaryParameter(name);
			if (value == null) {
				value = dv;
			}

			x.push(value);
		});

		functions.put("tparamSet", (x) -> {
			String name = x.pop().toString();
			String value = x.pop().toString();

			requestContext.setTemporaryParameter(name, value);
		});
		 
		functions.put("tparamDel", (x) -> {
			String name = x.pop().toString();

			requestContext.removeTemporaryParameter(name);
		});
		 
		functions.put("+", (x) -> {
			ValueWrapper first = new ValueWrapper(x.pop());
			ValueWrapper second = new ValueWrapper(x.pop());
			first.add(second.getValue());
			x.push(first.getValue());
		});
		
		functions.put("-", (x) -> {
			ValueWrapper first = new ValueWrapper(x.pop());
			ValueWrapper second = new ValueWrapper(x.pop());
			first.subtract(second.getValue());
			x.push(first.getValue());
		});		
		
		functions.put("*", (x) -> {
			ValueWrapper first = new ValueWrapper(x.pop());
			ValueWrapper second = new ValueWrapper(x.pop());
			first.multiply(second.getValue());
			x.push(first.getValue());
		});
		
		functions.put("/", (x) -> {
			ValueWrapper first = new ValueWrapper(x.pop());
			ValueWrapper second = new ValueWrapper(x.pop());
			first.divide(second.getValue());
			x.push(first.getValue());
		});
		 
	}
	
	
	/**
	 * A visitor to visit all nodes and execute appropriate operations
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ex) {
				throw new RuntimeException("Unable to write to stream", ex);
			}
			
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String varName = node.getVariable().getName();
			multistack.push(varName, new ValueWrapper(node.getStartExpression().getElementValue()));
			
			ValueWrapper step = new ValueWrapper(node.getStepExpression().getElementValue());
			ValueWrapper end = new ValueWrapper(node.getEndExpression().getElementValue());
			
			while (multistack.peek(varName).numCompare(end.getValue()) <= 0) {
				for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
					node.getChild(i).accept(this);
				}
				
				multistack.peek(varName).add(step.getValue());
			}
			
			multistack.pop(varName);			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Deque<Object> stack = new LinkedList<>();
			
			for (Element element : node.getElements()) {
				if (element instanceof ElementOperator) {
					operatorMethod((ElementOperator)element, stack);
				} else if (element instanceof ElementFunction) {
					functionMethod((ElementFunction)element, stack);
				} else if (element instanceof ElementVariable) {
					variableMethod((ElementVariable)element, stack);
				} else {
					try {
						stack.push(element.getElementValue());
					} catch (RuntimeException ex) {
						throw new IllegalArgumentException("Unsupported element type", ex);
					}
				}
				
			}
			while (!stack.isEmpty()) {
				try {
					requestContext.write(stack.removeLast().toString());
				} catch (IOException ex) {
					throw new RuntimeException("Unable to write to the stream", ex);
				}
			}
			
		}		

		private void functionMethod(ElementFunction element, Deque<Object> stack) {
			String function = element.getName();
			if (!functions.containsKey(function)) {
				throw new RuntimeException("Unknown function: " + function);
			}
			functions.get(function).accept(stack);			
		}

		private void variableMethod(ElementVariable element, Deque<Object> stack) {
			Object value = multistack.peek(element.getName()).getValue();
			if (value == null) {
				value = 0;
			}			
			stack.push(value);
			
		}

		private void operatorMethod(ElementOperator oper, Deque<Object> stack) {
			String operator = oper.getSymbol();
			if (!functions.containsKey(operator)) {
				throw new RuntimeException(oper.getSymbol() + " is not a supported operation.");
			}
			
			functions.get(operator).accept(stack);		
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			int size = node.numberOfChildren();
			for (int i = 0; i < size; i++) {
				node.getChild(i).accept(this);
			}			
		}
	};

	/**
	 * A constructor
	 * 
	 * @param documentNode A top-level node of a document that should be executed
	 * @param requestContext context of a request
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		if (documentNode == null || requestContext == null) {
			throw new IllegalArgumentException("Arguments can't be null");
		}
		
		this.documentNode = documentNode;
		this.requestContext = requestContext;
		
		multistack = new ObjectMultistack();
		
		initFunctions();
	}

	/**
	 * Execute initialized script
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
