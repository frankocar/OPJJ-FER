package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawingModel.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * A model for a list of drawing objects that acts as a listener on a model
 * 
 * @author Franko Car
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**  */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link DrawingModel} used
	 */
	private DrawingModel model;
	
	/**
	 * A constructor
	 * 
	 * @param model A model to observe
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}
	
	@Override
	public GeometricalObject getElementAt(int arg0) {
		return model.getObject(arg0);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);		
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);	
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);	
	}
	
	/**
	 * Returns the model used
	 * 
	 * @return DrawingModel used
	 */
	public DrawingModel getDrawingModel() {
		return model;
	}

	
}
