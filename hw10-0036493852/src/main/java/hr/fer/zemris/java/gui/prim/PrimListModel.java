package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * An implementation of ListModel that calculates and stores primes as integers
 * 
 * @author Franko Car
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/** List of listeners */
	private List<ListDataListener> listeners;
	/** Stored primes */
	private List<Integer> primes;
	/** Last generated prime */
	private int previous;
	
	
	/**
	 * A default constructor
	 */
	public PrimListModel() {
		super();
		this.listeners = new CopyOnWriteArrayList<>();
		this.primes = new ArrayList<>();
		previous = 1;
		primes.add(previous);
	}

	/**
	 * A method to generate next prime and add it to the model
	 */
	public void next() {		
		while(!isPrime(++previous));
		primes.add(previous);
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, primes.size() - 1, primes.size() - 1);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}
	
	/**
	 * A method to check if a number is prime
	 * 
	 * @param x number to check
	 * @return boolean true if prime, false otherwise
	 */
	public static boolean isPrime(int x) { //public for testing
		for (int i = 2, n = (int) Math.sqrt(x); i <= n; i++) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public Integer getElementAt(int index) {
		if (index < 0 || index >= primes.size()) {
			throw new IllegalArgumentException("Requested an elements out of bounds");
		}
		
		return primes.get(index);
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}
	
	/** 
	 * A getter for last generated prime
	 * 
	 * @return last prime
	 */
	public int getPrevious() { //required for testing
		return previous;
	}	
	
}
