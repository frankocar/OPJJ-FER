package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * A simple calculator using a CalcLayout layout.
 * It features a stack memory using push and pop buttons.
 * 
 * By selecting the "inv" checkbox it's possible to use inverses 
 * of some functions:
 * 		log => 10^
 * 		ln => e^x
 * 		x^n => x^(-n)
 * 		sin => asin
 * 		cos => acos
 * 		tan => atan
 * 		ctg => actg
 * 
 * "clr" resets the display, while "res" resets everything.
 * 
 * @author Franko Car
 *
 */
public class Calculator extends JFrame {

	/** Default UID */
	private static final long serialVersionUID = 1L;
	
	/** Inverse checkbox component */
	private JCheckBox inv;
	/** Display component */
	private JLabel display;
	
	/** Flag to mark current display as unmodifiable */
	private boolean notModifiable;
	/** Flag to mark that an operation is in progress */
	private boolean operationInProgress;
	
	/** Stack to use as memory */
	private Stack<String> stack;
	
	/** Last stored number for use in binary operations */
	private String prevNumber;
	/** Operator used for current operation */
	private Operations prevOper;
	
	/** Button color */
	private static final Color BTN_COLOR = new Color(60, 60, 60);
	/** Display color */
	private static final Color DISPLAY_COLOR = new Color(180,187,160);
	/** Background color */
	private static final Color BACKGROUND_COLOR = new Color(104,107,118);
	/** Button text color */
	private static final Color BTN_TEXT_COLOR = Color.WHITE;
	/** Button text font */
	private static final Font BTN_FONT = new Font("Arial", Font.BOLD, 18);
	
	/** Gap between elements */
	private static final int GAP = 4;
	
	
	/**
	 * Default constructor
	 */
	public Calculator() {
		this.notModifiable = false;
		stack = new Stack<>();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(600, 300);
		setTitle("Calculator");
		
		initGUI();
	}
	
	/**
	 * A method to initialize GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setBackground(BACKGROUND_COLOR);		
		cp.setLayout(new CalcLayout(GAP));
		
		display = new JLabel("0");
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.GRAY));
		display.setBackground(DISPLAY_COLOR);
		display.setOpaque(true);
		Font font = new Font("Arial", Font.BOLD, 22);
		display.setFont(font);
		add(display, new RCPosition(1, 1));
		
		inv = new JCheckBox("Inv");
		inv.setBackground(BTN_COLOR);
		inv.setForeground(Color.WHITE);
		inv.setFont(BTN_FONT);
		add(inv, new RCPosition(5, 7));
		
		addButtons();
		
		setMinimumSize(getMinimumSize());
	}

	/**
	 * A method to add required buttons
	 */
	private void addButtons() {
		//numbers		
		newButton("1", new RCPosition(4, 3), x -> numberButton("1"));
		newButton("2", new RCPosition(4, 4), x -> numberButton("2"));
		newButton("3", new RCPosition(4, 5), x -> numberButton("3"));
		newButton("4", new RCPosition(3, 3), x -> numberButton("4"));
		newButton("5", new RCPosition(3, 4), x -> numberButton("5"));
		newButton("6", new RCPosition(3, 5), x -> numberButton("6"));
		newButton("7", new RCPosition(2, 3), x -> numberButton("7"));
		newButton("8", new RCPosition(2, 4), x -> numberButton("8"));
		newButton("9", new RCPosition(2, 5), x -> numberButton("9"));
		newButton("0", new RCPosition(5, 3), x -> numberButton("0"));
		
		newButton(".", new RCPosition(5, 5), x -> dot());		
		
		//unary functions
		newButton("1/x", new RCPosition(2, 1), new UnaryOperatorListener(x -> 1.0/x));
		newButton("log", new RCPosition(3, 1), new UnaryOperatorListener(Math::log10, x -> Math.pow(10, x)));
		newButton("ln", new RCPosition(4, 1), new UnaryOperatorListener(Math::log, Math::exp));
		newButton("sin", new RCPosition(2, 2), new UnaryOperatorListener(Math::sin, Math::asin));
		newButton("cos", new RCPosition(3, 2), new UnaryOperatorListener(Math::cos, Math::acos));
		newButton("tan", new RCPosition(4, 2), new UnaryOperatorListener(Math::tan, Math::atan));
		newButton("ctg", new RCPosition(5, 2), new UnaryOperatorListener(x -> 1 / Math.tan(x), x -> 1 / Math.atan(x)));
		newButton("+/-", new RCPosition(5, 4), new UnaryOperatorListener(x -> x * -1));
		
		//binary functions
		newButton("=", new RCPosition(1, 6), x -> equals());
		newButton("/", new RCPosition(2, 6), x -> binaryOperation(Operations.DIV));
		newButton("*", new RCPosition(3, 6), x -> binaryOperation(Operations.MUL));
		newButton("-", new RCPosition(4, 6), x -> binaryOperation(Operations.SUB));
		newButton("+", new RCPosition(5, 6), x -> binaryOperation(Operations.ADD));
		newButton("x^n", new RCPosition(5, 1), x -> binaryOperation(Operations.POWER));
		
		//memory operations
		newButton("clr", new RCPosition(1, 7), x -> display.setText("0"));
		newButton("res", new RCPosition(2, 7), x -> {
			prevNumber = null;
			prevOper = null;
			operationInProgress = false;
			notModifiable = false;
			display.setText("0");
			stack.clear();
		});
		newButton("push", new RCPosition(3, 7), x -> stack.push(display.getText()));
		newButton("pop", new RCPosition(4, 7), x -> {
			if (stack == null || stack.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No elements left to pop", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			display.setText(stack.pop());
		});
		
	}
	
	/**
	 * A method to type numbers using number buttons
	 * 
	 * @param number
	 */
	private void numberButton(String number) {
		if (display.getText().equals("0") || notModifiable) {
			display.setText(number);
			notModifiable = false;
		} else {
			display.setText(display.getText() + number);
		}
	}

	/**
	 * A method to execute a binary operation
	 * 
	 * @param oper Operation symbol
	 */
	private void binaryOperation(Operations oper) {
		if (operationInProgress) {
			equals();
		}
		prevNumber = display.getText();
		prevOper = oper;
		operationInProgress = true;
		notModifiable = true;
		
	}

	/**
	 * A method to calculate the result, as a step in between operations
	 * or when equals is pressed
	 */
	private void equals() {
		if (prevNumber == null) {
			return;
		}
		
		double first = Double.parseDouble(prevNumber);
		double second = Double.parseDouble(display.getText());
		
		switch (prevOper) {
		case DIV:
			display.setText(Double.toString(first / second));
			break;
		case MUL:
			display.setText(Double.toString(first * second));
			break;
		case ADD:
			display.setText(Double.toString(first + second));
			break;
		case SUB:
			display.setText(Double.toString(first - second));
			break;
		case POWER:
			if (inv.isSelected()) {
				display.setText(Double.toString(Math.pow(first, -second)));
			} else {
				display.setText(Double.toString(Math.pow(first, second)));
			}
			break;
		}
		notModifiable = true;
		operationInProgress = false;
	}

	/**
	 * A method to support valid behavior of decimal separator
	 */
	private void dot() {
		if (!display.getText().contains(".")) {
			display.setText(display.getText() + ".");
		}
	}

	/**
	 * A method to create a button with default colors on a given position with a given listener and a name
	 * 
	 * @param name
	 * @param position
	 * @param listener
	 */
	private void newButton(String name, RCPosition position, ActionListener listener) {
		JButton button = new JButton(name);
		button.addActionListener(listener);
		button.setBackground(BTN_COLOR);		
		button.setFont(BTN_FONT);
		button.setForeground(BTN_TEXT_COLOR);
		add(button, position);
		
		
	}

	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	
	/**
	 * A listener to support buttons representing unary invertible operations
	 * 
	 * @author Franko Car
	 *
	 */
	private class UnaryOperatorListener implements ActionListener {

		/** Default button function */
		private Function<Double, Double> function;
		/** Inverted button function */
		private Function<Double, Double> invertedFunction;
		
		/**
		 * A constructor for non-invertible operations
		 * 
		 * @param function basic function
		 */
		public UnaryOperatorListener(Function<Double, Double> function) {
			this(function, null);
		}
		
		/**
		 * A constructor for invertible functions
		 * 
		 * @param function basic function
		 * @param invertedFunction inverse function
		 */
		public UnaryOperatorListener(Function<Double, Double> function, Function<Double, Double> invertedFunction) {
			this.function = function;
			this.invertedFunction = invertedFunction;
		}



		@Override
		public void actionPerformed(ActionEvent e) {
			if (invertedFunction != null && inv.isSelected()) {
				display.setText(invertedFunction.apply(Double.parseDouble(display.getText())).toString());
			} else {
				display.setText(function.apply(Double.parseDouble(display.getText())).toString());
			}
			notModifiable = true;
		}
		
	}
	
}