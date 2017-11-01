package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * An implementation of a collection as an array list.
 * Duplicate elements are allowed, storage of null references isn't.
 * 
 * @author Franko Car
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Collection current size
	 */
	private int size;
	/**
	 * Collection current capacity
	 */
	private int capacity;
	/**
	 * An array storing elements of a collection
	 */
	private Object[] elements;
	
	/**
	 * Default constructor, will initialize a new Collection with a default capacity
	 */
	public ArrayIndexedCollection() {
		this(null, 16);
		
	}
	
	/**
	 * A constructor that will initialize a new Collection with a given capacity 
	 * 
	 * @param initialCapacity Capacity of a new collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		this(null, initialCapacity);
	}
	
	/**
	 * A constructor that will initialize a new Collection of a default size and add
	 * every element of anther given collection to it
	 * 
	 * @param other Collection from which to copy elements
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}

	/**
	 * 
	 * A constructor that will initialize a new Collection with a given capacity and add
	 * every element of anther given collection to it
	 * 
	 * @param other Collection from which to copy elements
	 * @param initialCapacity Capacity of a new collection
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this.capacity = initialCapacity;
		elements = new Object[initialCapacity];
		
		if(other != null) {
			this.addAll(other);
		}
	}
	
	/**
	 * Adds a value to the collection
	 * It will throw an exception if there is an attempt
	 * to store null
	 * 
	 * @param value Object to be added
	 * @throws IllegalArgumentException When the value is null
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Returns the object at a given index, it can be from 0 to the last element in a collection(size - 1). 
	 * If a given index is invalid, it will throw an IndexOutOfBounds exception. It works in O(1) time
	 * 
	 * @param index Index to get
	 * @return Value at a given index 
	 * @throws IndexOutOfBoundsException when a given index is not valid
	 */
	public Object get(int index) {
		if(index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Given position index is lower than 0 or higher than last element");
		}

		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		
		size = 0;
	}
	
	/**
	 * Inserts a value at a given position. Every value
	 * at that index and higher indices is shifted one place to the end.
	 * Will throw an exception if a null is given as a value or 
	 * if position is out of range(0 to last element in the list).
	 * Works in O(n) time.
	 * 
	 * @param value Value to be inserted
	 * @param position Position for a value to be inserted in
	 * @throws IllegalArgumentException When null is given as a value
	 * @throws IndexOutOfBoundsException When position is out of bounds
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("Value mustn't be null");
		}
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Given position index is lower than 0 or higher than last element");
		}

		if (size >= capacity) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
		
		for(int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		

		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns the index of a given element or -1 if it's not found in the collection.
	 * Works in O(n) time.
	 * 
	 * @param value Value to look for
	 * @return Index of the element or -1 if it's not found
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * A method that will remove an element at a given index and shift
	 * the collection to fill it's space. Throws an exception if the index
	 * is out of range.
	 * 
	 * @param index Index of the element to be removed
	 * @throws IndexOutOfBoundsException Index is invalid
	 */
	public void remove(int index) {
		if(index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Given position index is lower than 0 or higher than last element");
		}
		
		for(int i = index; i < (size - 1); i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[size - 1] = null;
		
		size--;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index < 0) {
			return false;
		}
		
		remove(index);
		return true;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {	
		if(indexOf(value) >= 0) {
			return true;
		}
		
		return false;
	}
	
}
