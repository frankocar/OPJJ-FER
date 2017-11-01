package hr.fer.zemris.java.hw16.jvdraw.drawingModel;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * An interface defining a drawing model. It allows for adding, removing and updating
 * objects, retrieving objects from a list, listener management and bounding box calculations
 * 
 * @author Franko Car
 *
 */
public interface DrawingModel {

	/**
	 * Returns the number of objects
	 * 
	 * @return number of objects
	 */
	int getSize();
	/**
	 * Returns an object at a given index
	 * 
	 * @param index index
	 * @return An object at an index
	 */
	GeometricalObject getObject(int index);
	/**
	 * Adds an object to the model
	 * 
	 * @param object an object to add
	 */
	void add(GeometricalObject object);
	/**
	 * Removes an object from the model
	 * 
	 * @param object an object to remove
	 */
	void remove(GeometricalObject object);
	/**
	 * Updates an object currently in the model
	 * 
	 * @param object an object to update
	 */
	void update(GeometricalObject object);
	
	/**
	 * Add a listener
	 * 
	 * @param l listener to add
	 */
	void addDrawingModelListener(DrawingModelListener l);
	/**
	 * Remove a listener
	 * 
	 * @param l listener to remove
	 */
	void removeDrawingModelListener(DrawingModelListener l);
	
	/**
	 * Calculates the edge coordinates of all objects and returns 
	 * them in a form of a simple array. First member of an array 
	 * represents minimum X coord, second is minimum y, third 
	 * maximum x and last maximum y value.
	 * 
	 * @return integer array representing a bounding box. {@code {minX, minY, maxX, maxY}} 
	 */
	int[] getBoundingBox();
	
}
