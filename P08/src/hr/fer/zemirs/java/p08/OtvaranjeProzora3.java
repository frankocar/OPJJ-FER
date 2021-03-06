package hr.fer.zemirs.java.p08;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class OtvaranjeProzora3 {

	public static void main(String[] args) {
		Runnable posao = new Runnable() {

			@Override
			public void run() {
				System.out.println("Ja sam " + Thread.currentThread().getName());
				JFrame frame = new JFrame();
				
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				frame.setLocation(20, 20);
				frame.setSize(500, 200);
				
				frame.setVisible(true);
				
			}
		};
		
		SwingUtilities.invokeLater(posao);
	}
		
	
}
