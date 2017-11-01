package hr.fer.zemris.java.p09;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor1 extends JFrame{

	/**  */
	private static final long serialVersionUID = 1L;

	public Prozor1() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(500, 300);
		setTitle("Prozor 1");
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int stisunto = JOptionPane.showConfirmDialog(
						Prozor1.this,
						"Dragi korisniče, jesi li siguran da želiš zatvoriti aplikaciju?",
						"Poruka sustava",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				
				if (stisunto != JOptionPane.YES_OPTION) {
					return;
				}
				
				dispose();
			}
		});
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Prozor1().setVisible(true));
	}
	
}
