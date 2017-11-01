package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of a collection as a linked list.
 * Duplicate elements are allowed, storage of null references isn't.
 * 
 * @author Franko Car
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Local method to store each node
	 * 
	 * @author Franko Car
	 *
	 */
	private static class ListNode {
		/**
		 * Reference to the next node
		 */
		ListNode nextNode;
		/**
		 * Reference to the previous node
		 */
		ListNode previousNode;
		/**
		 * Contained value
		 */
		Object value;
		
		/**
		 * Constructor for a node
		 * 
		 * @param value Contained value
		 * @param next Reference to the next node
		 * @param previous Reference to the previous node
		 */
		ListNode(Object value, ListNode next, ListNode previous) {
			this.nextNode = next;
			this.previousNode = previous;
			this.value = value;
		}
	}
	
	/**
	 * Size of a collection
	 */
	private int size;
	/**
	 * Reference to the first node in the list
	 */
	private ListNode first;
	/**
	 * Reference to the last node in the list
	 */
	private ListNode last;
	
	/**
	 * A constructor that takes in another collection and adds its elements to the new list
	 * 
	 * @param other Other collection to copy elements from
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.size = 0;
		this.first = null;
		this.last = null;
		
		if(other != null) {
			addAll(other);
		}
	}
	
	/**
	 * A default constructor
	 */
	public LinkedListIndexedCollection() {
		this(null);
	}
	
	@Override
	public int size() {
		return size;
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
		if(value == null) {
			throw new IllegalArgumentException("'null' can't be stored");
		}
		
		if(first == null) {
			first = new ListNode(value, null, null);
			last = first;
			size++;
			return;
		}
		
		ListNode newNode = new ListNode(value, null, last);
		last.nextNode = newNode;
		last = newNode;
		size++;		
	}
	
	/**
	 * Returns the object at a given index, it can be from 0 to the last element in a collection(size - 1). 
	 * If a given index is invalid, it will throw an IndexOutOfBounds exception. Works in O(n/2 + 1) time
	 * 
	 * @param index Index of a desired element
	 * @return Returns the element stored at a given index
	 * @throws IndexOutOfBoundsException when index is out of range
	 */
	public Object get(int index) {
		return getNode(index).value;
	}
	
	/**
	 * A method that will return a node at a given index
	 * Works in O(n/2 + 1) time
	 * 
	 * @param index Index of a desired node
	 * @return Reference to the node at a given index
	 * @throws IndexOutOfBoundsException when index is out of range
	 */
	private ListNode getNode(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException("Given index is not a valid entry");
		}
				
		if(index <= size/2) {
			ListNode node = first;
			for(int i = 0; i < index; i++) {
				node = node.nextNode;
			}
			return node;
		} else {
			ListNode node = last;
			for(int i = (size - 1); i > index; i--) {
				node = node.previousNode;
			}
			return node;
		}
	}
	
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
		
		//removes the references to the first and last node, 
		//garbage collector should do the rest
	}
	
	/**
	 * Inserts a value at a given position. Every value
	 * at that index and higher indices is shifted towards the end.
	 * Will throw an exception if a null is given as a value or 
	 * if position is out of range(0 to last element in the list).
	 * Works in O(n/2 + 1) time.
	 * 
	 * @param value Value to be inserted
	 * @param position Position for a value to be inserted in
	 * @throws IllegalArgumentException When null is given as a value
	 * @throws IndexOutOfBoundsException When position is out of bounds
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new IllegalArgumentException("'null' can't be stored");
		}
		
		if(position == size) {
			add(value);
			return;
		}
		
		ListNode oldNode = getNode(position);
		ListNode newNode = new ListNode(value, oldNode, oldNode.previousNode);
		
		if(oldNode.previousNode != null) {
			oldNode.previousNode.nextNode = newNode;
			oldNode.previousNode = newNode;
		} else {
			first = newNode;
		}
				
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
		ListNode temp = first;
		int index = 0;
		while(temp != null) {
			if(temp.value.equals(value)) {
				return index;
			}
			index++;
			temp = temp.nextNode;
		}
		
		return -1;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) < 0 ? false : true;
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
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		ListNode node = first;
		for(int i = 0; i < size; i++) {
			array[i] = node.value;
			node = node.nextNode;
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode node = first;
		for(int i = 0; i < size; i++) {
			processor.process(node.value);
			node = node.nextNode;
		}
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
		ListNode nodeToRemove = getNode(index);
		
		if(index == 0) {
			first = nodeToRemove.nextNode;
			size--;
			return;
		}
		
		if(index == (size - 1)) {
			last = nodeToRemove.previousNode;
			last.nextNode = null;
			size--;
			return;
		}
		
		nodeToRemove.previousNode.nextNode = nodeToRemove.nextNode;
		nodeToRemove.previousNode = nodeToRemove.previousNode;
		nodeToRemove = null;
		
		size--;
	}
	
}
