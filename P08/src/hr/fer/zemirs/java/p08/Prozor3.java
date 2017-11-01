package hr.fer.zemirs.java.p08;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Prozor3 extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Prozor3() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(20, 20);
		setSize(500, 200);
		setTitle("Moj prvi prozor");
		
		initGUI();
		
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		
		cp.setLayout(null);
		
		Sat sat = new Sat();
		
		sat.setBounds(10, 10, 300, 50);
		sat.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		cp.add(sat);
		
	}
	
	private static class Sat extends JComponent {
		/** */
		private static final long serialVersionUID = 1L;
		private LocalTime vrijeme;
		
		public Sat() {
			Thread radnik = new Thread(() -> {
				while (true) {
					SwingUtilities.invokeLater(() -> {
						vrijeme = LocalTime.now();
						repaint();
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ignorable) {
					}
				}
			});
			radnik.start();
		}
		
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
			
			if (vrijeme == null) {
				return;
			}
			
			g.setColor(getForeground());
			
			String str = vrijeme.toString();
			
			FontMetrics fm = g.getFontMetrics();
			
			g.drawString(str,
					(dim.width - ins.left - ins.right-fm.stringWidth(str))/2 + ins.left,
					(dim.height-ins.top-ins.bottom-fm.getAscent()) / 2 + fm.getAscent() + ins.top);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor3().setVisible(true);
		});
	}

}
