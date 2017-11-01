package hr.fer.zemris.java.hw16.jvdraw.drawingModel;

/**
 * DrawingModel listener interface
 * 
 * @author Franko Car
 *
 */
public interface DrawingModelListener {

	/**
	 * Method to notify listeners about a new object
	 * 
	 * @param source Change source
	 * @param index0 Index of the first affected object
	 * @param index1 Index of the last affected object
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Method to notify listeners about a removed object
	 * 
	 * @param source Change source
	 * @param index0 Index of the first affected object
	 * @param index1 Index of the last affected object
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Method to notify listeners about a changed object
	 * 
	 * @param source Change source
	 * @param index0 Index of the first affected object
	 * @param index1 Index of the last affected object
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
	
}
