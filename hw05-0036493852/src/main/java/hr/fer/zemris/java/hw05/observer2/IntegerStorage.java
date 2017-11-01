package hr.fer.zemris.java.hw05.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that will keep track of registered observers and notify them
 * when the value of stored integer has changed.
 * 
 * @author Franko Car
 *
 */
public class IntegerStorage {
	/**
	 * Current value
	 */
	private int value;
	/**
	 * List of registered observers
	 */
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	/**
	 * A constructor that will take in initial value
	 * 
	 * @param initialValue First value stored
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * A method to register a new observer
	 * 
	 * @param observer Observer to register
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<IntegerStorageObserver>();
		}
		
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
		
	}

	/**
	 * A method to remove an observer
	 * 
	 * @param observer Observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			return;
		}
		observers.remove(observer);
	}

	/**
	 * A method to remove all observers 
	 */
	public void clearObservers() {
		if (observers == null) {
			return;
		}
		observers.clear();
	}

	/**
	 * A getter for value
	 * 
	 * @return int Stored value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * A setter for value
	 * 
	 * @param value New value
	 */
	public void setValue(int value) {
		if (this.value != value) {			
			int oldValue = this.value;			
			this.value = value;
			
			if (observers != null) {
				/* this way, a new list is created to iterate over, so that observers can be
				 * registered or unregistered during iteration, without changing the list
				 * currently being iterated over*/
				List<IntegerStorageObserver> oldObservers = new ArrayList<>(observers);
				IntegerStorageChange storageChange = new IntegerStorageChange(this, oldValue, value);
				for (IntegerStorageObserver observer : oldObservers) {
					observer.valueChanged(storageChange);
				}
			}
		}
	}
}