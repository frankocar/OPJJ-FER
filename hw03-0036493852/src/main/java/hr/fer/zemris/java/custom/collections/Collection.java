package hr.fer.zemris.java.custom.collections;

/**
 * Basic framework for a simple collection implementation
 * 
 * @author Franko Car
 *
 */
public class Collection {
	
	/**
	 * A protected default constructor, does nothing.
	 */
	protected Collection() {
		
	}

	/**
	 * A method that will return true if the collection it's 
	 * called upon is empty, false if it contains something.
	 * 
	 * @return Boolean true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the number of elements currently stored in a collection
	 * 
	 * @return number of elements
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds a value to the collection
	 * 
	 * @param value Object to be added
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks whether a given value is contained in a collection.
	 * 
	 * @param value A value to check
	 * @return True if value was found, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	
	/**
	 * Removes a single occurrence of a given value from the collection and returns
	 * a boolean to confirm
	 * 
	 * @param value Value to be removed
	 * @return Boolean, true if removed, false if the value is not contained
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Returns the collection as an array of appropriate size
	 * 
	 * @return An array of objects in a list
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Method should be used from a child class");
	}
	
	/**
	 * Processes each element of a collection with a given Processor object
	 * 
	 * @param processor A Processor that processes every value
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds every element from another collection into itself. The other collection
	 * will remain unchanged.
	 * 
	 * @param other Another collection
	 */
	public void addAll(Collection other) {
		/**
		 * Implementation of a processor that will add each 
		 * given value to the collection
		 */
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		Processor processor = new LocalProcessor();
		other.forEach(processor);
	}
	
	/**
	 * A method that will remove all elements from a collection
	 */
	public void clear() {
		
	}
 	
}
