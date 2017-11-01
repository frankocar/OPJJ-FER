package hr.fer.zemirs.java.p08;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor6 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor6() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel("Labela");
		JButton gumb = new JButton("Stisni me");
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(gumb, BorderLayout.PAGE_END);
		
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor6().setVisible(true);
		});
	}

}
