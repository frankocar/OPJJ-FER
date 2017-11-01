package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * A component that shows a {@link JColorChooser} on a click and shows a currently 
 * selected color as its representation. Also is an implementation of {@link IColorProvider}
 * interface.
 * 
 * @author Franko Car
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** */
	private static final long serialVersionUID = 1L;
	/**
	 * Currently selected colors
	 */
	private Color selectedColor;
	/**
	 * List of listeners
	 */
	private List<ColorChangeListener> listeners;	
	
	/**
	 * A constructor
	 * 
	 * @param selectedColor initial color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		setBackground(selectedColor);
		setOpaque(true);
		setPreferredSize(new Dimension(15, 15));
		setMaximumSize(new Dimension(15, 15));
		setMinimumSize(new Dimension(15, 15));
		setBackground(selectedColor);		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color userColor = JColorChooser.showDialog(JColorArea.this.getParent(), "Choose a color", selectedColor);
				if (userColor != null) {
					setColor(userColor);
				}
			}			
		});		
		
		repaint();
	}
	
	/**
	 * Set a new color
	 * 
	 * @param newColor new color
	 */
	public void setColor(Color newColor) {
		if (newColor == null) {
			throw new IllegalArgumentException("Color can't be null");
		}
		
		Color oldColor = selectedColor;
		this.selectedColor = newColor;
		setBackground(selectedColor);
		repaint();
		
		if (listeners != null && !listeners.isEmpty()) {
			List<ColorChangeListener> iterable = new LinkedList<>(listeners);
			for (ColorChangeListener listener : iterable) {
				listener.newColorSelected(this, oldColor, selectedColor);
			}
		}
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (listeners == null) {
			listeners = new LinkedList<>();
		}
		
		listeners.add(l);		
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		if (listeners == null) {
			return;
		}
		
		listeners.remove(l);		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
