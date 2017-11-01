package hr.fer.zemris.java.custom.collections;

import hr.fer.zemris.java.custom.collections.EmptyStackException;

/**
 * Implementation of stack data collection.
 * Supports push, pop and peek.
 * 
 * @author Franko Car
 *
 */
public class ObjectStack {

	/**
	 * Array used as data storage
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * A constructor to initialize the stack
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns true if the stack is empty, false otherwise
	 * 
	 * @return True if stack is empty, false otherwise
	 */
	public boolean isEmpty(){
		return collection.isEmpty();
	}
	
	/**
	 * Returns the number of elements stored on the stack
	 * 
	 * @return Number of elements
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Adds an element to the top of the stack.
	 * Should work in O(1) time.
	 * 
	 * @param value Value to add
	 */
	public void push(Object value) {
		collection.add(value);
	}
	
	/**
	 * Retrieves the element from the top of the stack.
	 * Should work in O(1) time.
	 * 
	 * @return Element from the top of the stack
	 */
	public Object pop() {
		if (collection.isEmpty()) {
			throw new EmptyStackException("The stack is empty");
		}
		
		Object element = collection.get(collection.size() - 1);
		collection.remove(collection.size() - 1);
		return element;
	}
	
	/**
	 * Peeks at the element at the top of the stack, doesn't remove it.
	 * Should work in O(1) time.
	 * 
	 * @return The element at the top
	 */
	public Object peek() {
		if (collection.isEmpty()) {
			throw new EmptyStackException("The stack is empty");
		}
		
		return collection.get(collection.size() - 1);
	}
	
	/**
	 * Removes everything from the stack.
	 */
	public void clear() {
		collection.clear();
	}
	
}
