package hr.fer.zemirs.java.p08;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor2 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
	}
	
	private static class MojaKomponenta extends JComponent {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			Dimension dim = getSize();
			Insets ins = getInsets();
			
			if (isOpaque()) {
				g.setColor(getBackground());
				g.fillRect(
						ins.left, 
						ins.top, 
						dim.width -ins.left - ins.right, 
						dim.height - ins.top - ins.bottom);
			}
			
			g.setColor(getForeground());
			g.fillOval(
					ins.left, 
					ins.top, 
					dim.width -ins.left - ins.right, 
					dim.height - ins.top - ins.bottom);
			g.drawLine(ins.left, ins.top, dim.width - ins.right, dim.height - ins.bottom);
			g.drawLine(ins.left, dim.height - ins.bottom, dim.width - ins.right, ins.top);
		}
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(null);
		for (int i= 0; i < 5; i++) {
			JComponent komponenta = new MojaKomponenta() {

				/** */
				private static final long serialVersionUID = 1L;};
				komponenta.setLocation(20 + i *20, 30 + i * 40);
				komponenta.setSize(200, 30);
				komponenta.setBorder(BorderFactory.createLineBorder(Color.BLUE));
				komponenta.setBackground(Color.YELLOW);
				komponenta.setOpaque(i % 2 == 0);
				komponenta.setForeground(Color.RED);
				cp.add(komponenta);
			}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor2().setVisible(true);
		});
	}

}
