package hr.fer.zemirs.java.p08;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor5 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor5() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(null);
		
		JLabel labela = new JLabel("Labela");
		JButton gumb = new JButton("Stisni me");
		
		cp.add(labela);
		cp.add(gumb);
		
		labela.setLocation(10, 20);
		labela.setSize(labela.getPreferredSize());
		
		gumb.setLocation(labela.getX(), labela.getY() + labela.getHeight());
		gumb.setSize(gumb.getPreferredSize());
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor5().setVisible(true);
		});
	}

}
