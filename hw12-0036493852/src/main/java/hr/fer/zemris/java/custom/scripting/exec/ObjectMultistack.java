package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of multistack collection that will store any {@link ValueWrapper}
 * object stored under a key of type string. Keys mustn't be null and the {@link ValueWrapper}
 * itself mustn't be null(but value stored in it may be, as described in {@link ValueWrapper}).
 * Works in O(1).
 * 
 * @author Franko Car
 *
 */
public class ObjectMultistack {

	/**
	 * A map to store the data
	 */
	private Map<String, MultistackEntry> map;
	
	/**
	 * A default constructor
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}
	
	/**
	 * A method to push a new value to the stack of a given name
	 * 
	 * @param name Stack to push to
	 * @param valueWrapper Value to store
	 * @throws IllegalArgumentException when the name or value are null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null) {
			throw new IllegalArgumentException("Key mustn't be null");
		}
		
		if (valueWrapper == null) {
			throw new IllegalArgumentException("Wrapper mustn't be null");
		}
		
		if (map.containsKey(name)) {
			map.put(name, new MultistackEntry(valueWrapper, map.get(name)));
			return;
		}
		
		map.put(name, new MultistackEntry(valueWrapper, null));
	}
	
	/**
	 * A method to pop the value on the stack of a given name
	 * 
	 * @param name Stack to pop from
	 * @return ValueWrapper value popped
	 * @throws IllegalArgumentException When the given name is null
	 * @throws EmptyStackException If the stack is empty
	 */
	public ValueWrapper pop(String name) {
		checkName(name);
		
		MultistackEntry first = map.get(name);
		
		if (first.next == null) {
			map.remove(name);
		} else {
			map.put(name, first.next);
		}
		
		return first.value;
	}
	
	/**
	 * A method to peek at the value on the stack of a given name
	 * 
	 * @param name Stack to peek at
	 * @return ValueWrapper value peeked
	 * @throws IllegalArgumentException When the given name is null
	 * @throws EmptyStackException If the stack is empty
	 */
	public ValueWrapper peek(String name) {
		checkName(name);		
		return map.get(name).value;
	}
	
	/**
	 * A method to check if the given stack name is valid
	 * 
	 * @param name
	 * @throws IllegalArgumentException When the given name is null
	 * @throws EmptyStackException If the stack is empty
	 */
	private void checkName(String name) throws IllegalArgumentException, EmptyStackException {
		if (name == null) {
			throw new IllegalArgumentException("Key mustn't be null");
		}
		
		if (isEmpty(name)) {
			throw new EmptyStackException(); 
		}
	}
	
	/**
	 * Checks if the stack is empty
	 * 
	 * @param name Stack to check
	 * @return True if empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		if (!map.containsKey(name)) return true;
		if (map.get(name) == null) return true;
		return false;
	}
	
	/**
	 * A class to store stack entries as a linked list in a map
	 * 
	 * @author Franko Car
	 *
	 */
	private static class MultistackEntry {
		/**
		 * Stored value
		 */
		private ValueWrapper value;
		/**
		 * Next entry in list
		 */
		private MultistackEntry next;
		
		/**
		 * A constructor
		 * 
		 * @param value Value stored
		 * @param next Next entry in list.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
	}
	
}
