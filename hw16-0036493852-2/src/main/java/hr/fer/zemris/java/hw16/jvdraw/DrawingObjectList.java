package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * JList implementation that shows currently drawn objects and allows their modifications
 * 
 * @author Franko Car
 *
 */
public class DrawingObjectList extends JList<GeometricalObject> {

	/** */
	private static final long serialVersionUID = 1L;
	
	/**
	 * DrawingModel used
	 */
	private DrawingModel drawingModel;
	
	/**
	 * A constructor
	 * 
	 * @param model {@link DrawingObjectListModel} observed
	 */
	public DrawingObjectList(DrawingObjectListModel model) {
		setModel(model);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(120, getHeight()));
		drawingModel = model.getDrawingModel();
		
		init();
	}

	/**
	 * Initialization method
	 */
	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE && DrawingObjectList.this.getSelectedValue() != null) {
					DrawingObjectList.this.drawingModel
							.remove(DrawingObjectList.this.getSelectedValue());
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) return;
				
				GeometricalObject obj = DrawingObjectList.this.getSelectedValue();
				
				if (obj == null) return;
				
				int value = JOptionPane.showConfirmDialog(null, obj.getOptions(), obj.getName() + " options", JOptionPane.OK_CANCEL_OPTION);
				if (value == JOptionPane.OK_OPTION) {
					obj.applyOptions();
					drawingModel.update(obj);
				}
			}
		});
		
	}
	
}
