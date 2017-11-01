package hr.fer.zemirs.java.p08;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor7 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor7() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
		pack();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(new BorderLayout());
		
		JLabel labela = new JLabel("Labela");
		JPanel donjaPloca = new JPanel();
		donjaPloca.setLayout(new GridLayout(2, 2));
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(donjaPloca, BorderLayout.PAGE_END);
		
		ActionListener l = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				labela.setText(b.getText());
			}
		};
		JButton[] gumbi = new JButton[4];
		for (int i = 0; i < gumbi.length; i++) {
			gumbi[i] = new JButton("Stisni me " + (i + 1));
			donjaPloca.add(gumbi[i]);
			gumbi[i].addActionListener(l);
		}
		
		
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor7().setVisible(true);
		});
	}

}
