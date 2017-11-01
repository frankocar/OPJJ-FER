package hr.fer.zemris.java.hw04.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple implementation of a parameterized map. It maps a key to 
 * the value in O(1) time complexity. It doesn't permit null value
 * as a key, but it's possible for the value to be null. In case
 * that another value with a key that already exists in the table
 * needs to be added, the old value will be overwritten.
 * 
 * It implements {@code Iterable} interface, so iterating over every entry is
 * possible.
 * 
 * It will throw {@link IllegalArgumentException} when the key is null,
 * {@link ConcurrentModificationException} when iterating over the table
 * that has changed in the meantime, {@link NoSuchElementException} when
 * trying to get next element that hasn't been found yet and {@link IllegalStateException}
 * when trying to remove the same element twice using the iterators remove() method.
 * 
 * @author Franko Car
 *
 * @param <K> the type of keys used
 * @param <V> the type of values stored
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Current size of the collection
	 */
	private int size;
	/**
	 * A table used to store data
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of modifications made to make sure
	 * there are no changes during iteration
	 */
	private int modificationCount;
	/**
	 * A factor at which the table will be expanded
	 * to improve performance
	 */
	private static final double MAX_LOAD_FACTOR = 0.75;
	/**
	 * Starting size of a underlying table when using
	 * the default constructor
	 */
	private static final int DEFAULT_SIZE = 16;
	
	/**
	 * A class used to store each element of a collection	 * 
	 * 
	 * @author Franko Car
	 *
	 * @param <K> the type of keys used
	 * @param <V> the type of values stored
	 */
	public static class TableEntry<K,V> {
		/**
		 * A key in the table
		 */
		private K key;
		/**
		 * Assigned value
		 */
		private V value;
		/**
		 * Next element stored at the same table address
		 */
		private TableEntry<K,V> next;
		
		/**
		 * Constructor taking in the key, initial value and the next node in the list
		 * 
		 * @param key key
		 * @param value initial value
		 * @param next next node
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Key can't be null");
			}
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * A getter for the key
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * A gatter for the value 
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * A setter for the value
		 * 
		 * @param value new value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.format("%s=%s", key, value);
		}
	}
	
	/**
	 * Default constructor creating the table with DEFAULT_SIZE size
	 */
	public SimpleHashtable() {
		this(DEFAULT_SIZE);
	}
	
	/**
	 * A constructor taking in the initial capacity needed
	 * 
	 * @param initialCapacity initial capacity of the table
	 * @throws IllegalArgumentException when desired size is less then 0
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException("Size can't be less than 0");
		}
		
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity *= 2;
		}
		
		size = 0;	
		modificationCount = 0;
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}
	
	/**
	 * A method that will calculate the address of a 
	 * given key
	 * 
	 * @param key A key to calculate the address for
	 * @return Address in the table
	 */
	private int keyHash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * A method to put new value in the table
	 * 
	 * @param key Desired key
	 * @param value Stored value
	 * @throws IllegalArgumentException when given key is null
	 */
	public void put(K key, V value){
		if (key == null) {
			throw new IllegalArgumentException("Key mustn't be null");
		}
		
		int hash = keyHash(key);
		
		if (table[hash] == null) {
			table[hash] = new TableEntry<>(key, value, null);
			modificationCount++;
			size++;
		} else {
			TableEntry<K, V> entry = table[hash];
			
			while (entry.next != null && !entry.getKey().equals(key)) {
				entry = entry.next;
			}
			
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				modificationCount++;
			} else {
				entry.next = new TableEntry<K, V>(key, value, null);
				modificationCount++;
				size++;
			}
		}
		
		if (size >= table.length * MAX_LOAD_FACTOR) {
			doubleSize();
		}
	}
	
	/**
	 * A method that will double the size of the table when
	 * MAX_LOAD_FACTOR is reached.
	 */
	@SuppressWarnings("unchecked")
	private void doubleSize() {
		TableEntry<K, V>[] old = table;
		table = (TableEntry<K, V>[]) new TableEntry[2 * old.length];
		size = 0;
		
		for (int i = 0; i < old.length; i++) {
			TableEntry<K, V> temp = old[i];
			while(temp != null) {
				put(temp.key, temp.value);
				temp = temp.next;
			}
		}		
 	}
	
	/**
	 * A method that will clear the table
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = (TableEntry<K, V>[]) new TableEntry[table.length]; 
		size = 0;
	}
	
	/**
	 * A method that will return the value stored under
	 * the given key. Will return null if the value doesn't
	 * exist.
	 * 
	 * @param key Key of the element
	 * @return Stored value
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int hash = keyHash(key);

		TableEntry<K, V> entry = table[hash];

		while (entry != null && !entry.getKey().equals(key)) {
			entry = entry.next;
		}

		if (entry == null) {
			return null;
		}
		
		return entry.getValue();
	}
	
	/**
	 * A method to remove the element with the given key
	 * 
	 * @param key Key of an element to remove
	 */
	public void remove(Object key) {		
		int hash = keyHash(key);

		if (table[hash] != null) {
			TableEntry<K, V> previousEntry = null;
			TableEntry<K, V> entry = table[hash];

			while (entry.next != null && !entry.getKey().equals(key)) {
				previousEntry = entry;
				entry = entry.next;
			}

			if (entry.getKey().equals(key)) {
				if (previousEntry == null) {
					table[hash] = entry.next;
				} else {
					previousEntry.next = entry.next;
				}
				modificationCount++;
				size--;
			}
		}
	}
	
	/**
	 * A method that will check if a collection contains a key
	 * 
	 * @param key Key of an element to look for
	 * @return True if found, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		
		int hash = keyHash(key);

		TableEntry<K, V> entry = table[hash];

		while (entry != null && !entry.getKey().equals(key)) {
			entry = entry.next;
		}

		return entry != null;
	}
	
	/**
	 * A method that will check if a collection contains a value
	 * 
	 * @param value Value of an element to look for
	 * @return True if found, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> entry : table) {
			if (entry == null) {
				continue;
			}

			TableEntry<K, V> temp = entry;
			while (temp != null) {
				if (temp.getValue().equals(value)) {
					return true;
				}
				temp = temp.next;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a collection is empty
	 * 
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the current size of a collection
	 * 
	 * @return Size of a collection
	 */
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (TableEntry<K, V> entry : table) {
			if (entry == null) {
				continue;
			}
			
			TableEntry<K, V> temp = entry;
			while (temp != null) {
				sb.append(temp).append(", ");
				temp = temp.next;
			}
		}
		
		if (sb.length() < 2) {
			return "[]";
		}
		
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		
		return sb.toString();
		
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * An implementation od {@code Iterator} interface appropriate for this class.
	 * It will check for concurrent modifications and throw {@link ConcurrentModificationException}
	 * if one happens. It throws {@link IllegalStateException} when trying to remove an element more 
	 * than once and {@link NoSuchElementException} when trying to return an element that hasn't
	 * been found yet
	 * 
	 * @author Franko Car
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		/**
		 * Current element found
		 */
		private TableEntry<K, V> current;
		/**
		 * Next element in the collection
		 */
		private TableEntry<K, V> next;
		/**
		 * Current slot of the table
		 */
		private int slot;
		/**
		 * Number of modifications when the iteration started
		 */
		private int initialModCount;
		
		/**
		 * A constructor that will initialize the modifications
		 * count and find the first slot in the table that isn't
		 * empty.
		 */
		public IteratorImpl() {
			slot = 0;
			initialModCount = modificationCount;
			
			if (size > 0) {
				while (slot < table.length && (next = table[slot++]) == null);
			}
		}
		
		@Override
		public boolean hasNext() {
			if (initialModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection modified during iteration");
			}
			
			return next != null;
		}

		@Override
		public TableEntry<K, V> next() {
			if (initialModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection modified during iteration");
			}
			
			if (next == null) {
				throw new NoSuchElementException("No more elements in the collection");
			}
			
			TableEntry<K, V> temp = next;
			next = next.next;
			if (next == null) {
				while (slot < table.length && (next = table[slot++]) == null);
			}
			current = temp;
			return temp;
			
			
		}
		
		@Override
		public void remove() {
			if (initialModCount != modificationCount) {
				throw new ConcurrentModificationException("Collection modified during iteration");
			}
			
			if (current == null) {
				throw new IllegalStateException("Can't delete a removed element");
			}
			
			SimpleHashtable.this.remove(current.key);			
			current = null;
			initialModCount = modificationCount;
		}
		
	}
	
}
