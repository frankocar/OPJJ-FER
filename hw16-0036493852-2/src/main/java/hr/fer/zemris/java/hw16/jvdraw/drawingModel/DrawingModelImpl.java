package hr.fer.zemris.java.hw16.jvdraw.drawingModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Simple drawing model interface implementation
 * 
 * @author Franko Car
 *
 */
public class DrawingModelImpl implements DrawingModel {

	/**
	 * List of object referenced in a model
	 */
	private List<GeometricalObject> objects;
	/**
	 * Model listeners
	 */
	private List<DrawingModelListener> listeners;
	
	/**
	 * A constructor
	 */
	public DrawingModelImpl() {
		objects = new CopyOnWriteArrayList<>();
		listeners = new CopyOnWriteArrayList<>();
	}
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= objects.size()) {
			throw new IllegalArgumentException("Index is out of range");
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		int i = objects.size() - 1;
		
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, i, i);
		}
	}
	
	@Override
	public void remove(GeometricalObject object) {
		int i = objects.indexOf(object);
		objects.remove(object);
		
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, i, i);
		}
	}
	
	@Override
	public void update(GeometricalObject object) {
		int i = objects.indexOf(object);
		
		if (i < 0) {
			add(object);
			return;
		}
		
		
		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, i, i);
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);

	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public int[] getBoundingBox() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (GeometricalObject obj : objects) {
			minX = Math.min(minX, obj.getMinX());
			minY = Math.min(minY, obj.getMinY());
			maxX = Math.max(maxX, obj.getMaxX());
			maxY = Math.max(maxY, obj.getMaxY());
		}
		
		return new int[]{minX, minY, maxX, maxY};
	}

}
