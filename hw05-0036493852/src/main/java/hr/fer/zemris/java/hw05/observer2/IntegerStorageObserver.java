package hr.fer.zemris.java.hw05.observer2;

/**
 * An interface that will allow classes that implement it
 * to be notified when an action happens
 * 
 * @author Franko Car
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * A method to notify an object that the stored value has changed.
	 * 
	 * @param istorage {@link IntegerStorageChange} to which the observer object is registered to
	 */
	public void valueChanged(IntegerStorageChange istorage);
	
}
