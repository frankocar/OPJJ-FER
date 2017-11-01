package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * A simple program to show two lists sharing a model 
 * with ability to generate and add prime numbers
 * 
 * @author Franko Car
 *
 */
public class PrimDemo extends JFrame{

	/** Default UID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Main method
	 * 
	 * @param args none required
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

	/**
	 * Default constructor
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(250, 150);
		setTitle("PrimDemo");
		
		initGUI();
		
	}
	
	/**
	 * A method to initialize the GUI
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		JList<Integer> listLeft = new JList<>(model);
		JList<Integer> listRight = new JList<>(model);
		
		JPanel listPanel = new JPanel(new GridLayout(1, 2));
		
		listPanel.add(new JScrollPane(listLeft));
		listPanel.add(new JScrollPane(listRight));
		
		cp.add(listPanel, BorderLayout.CENTER);
		
		JButton button = new JButton("Next prime");
		cp.add(button, BorderLayout.PAGE_END);
		
		button.addActionListener(e -> {
			model.next();
		});
		
	}
	
}
