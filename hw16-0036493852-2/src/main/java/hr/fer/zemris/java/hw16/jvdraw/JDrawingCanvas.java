package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModelListener;

/**
 * A model of a canvas, a component to draw on
 * 
 * @author Franko Car
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** */
	private static final long serialVersionUID = 1L;
	/**
	 * Drawing Model that is the source of changes
	 */
	private DrawingModel source;
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		this.source = source;
		
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		this.source = source;
		
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		this.source = source;
		
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (source == null) {
			return;
		}
		
		g.setColor(Color.BLACK);
		for (int i = 0, n = source.getSize(); i < n; i++) {
			source.getObject(i).draw((Graphics2D) g);
		}
	}

}
