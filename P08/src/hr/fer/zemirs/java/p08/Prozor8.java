package hr.fer.zemirs.java.p08;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor8 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor8() {
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
		donjaPloca.setLayout(new BorderLayout());
		
		cp.add(labela, BorderLayout.CENTER);
		cp.add(donjaPloca, BorderLayout.PAGE_END);
		
		donjaPloca.add(new JLabel("Unesite broj"), BorderLayout.LINE_START);
		
		JTextField tfBroj = new JTextField();
		JButton gumb = new JButton("Izracunaj");
		
		donjaPloca.add(tfBroj, BorderLayout.CENTER);
		donjaPloca.add(gumb, BorderLayout.LINE_END);
		
		gumb.addActionListener(e -> {
			String uneseno = tfBroj.getText();
			try {
				int broj = Integer.parseInt(uneseno);
				int kvadrat = broj * broj;
				labela.setText("Kvadrat broja je: " + kvadrat);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(
						this,
						"Unos '" + uneseno + "' nije cijeli broj",
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor8().setVisible(true);
		});
	}

}
